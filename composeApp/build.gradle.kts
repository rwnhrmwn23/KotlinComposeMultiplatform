import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerializer)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.nativeCocoapods)
}

val secretFolder = "$projectDir/build/generatedSecret"

kotlin {
    androidTarget {
//        @OptIn(ExperimentalKotlinGradlePluginApi::class)
//        compilerOptions {
//            jvmTarget.set(JvmTarget.JVM_1_8)
//            freeCompilerArgs.addAll(
//                "-P",
//                "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.bumble.appyx.utils.multiplatform.Parcelize"
//            )
//        }

        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.bumble.appyx.utils.multiplatform.Parcelize"
                )
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    cocoapods {
        version = "1.0"
        summary = "Some description for the ComposeApp"
        homepage = "Link to the project homepage"
        podfile = project.file("../iosApp/Podfile")

        ios.deploymentTarget = "17.0"

        framework {
            baseName = "ComposeApp"
            isStatic = true
        }

//        pod("netfox") {
//            extraOpts += listOf("-compiler-option", "-fmodules")
//            version = "1.21.0"
//        }
    }

    sourceSets.commonMain.configure {
        kotlin.srcDirs(secretFolder)
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Ktor
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.logging)

            // ViewModel
            implementation(libs.jetbrain.viewmodel)

            // Navigation
            implementation(libs.jetbrain.navigation)

            // Appyx
            implementation(libs.appyx.navigation)
            implementation(libs.appyx.interaction)
            implementation(libs.appyx.backstack)
        }
        iosMain.dependencies {
            // Ktor
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "org.onedev.kmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources", secretFolder)

    defaultConfig {
        applicationId = "org.onedev.kmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.12"
//    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

fun generateSecret(file: String) {
    val propContent = file("$rootDir/$file").readText()
    val propData = parseProp(propContent)

    var ktContent = "package org.onedev.kmp\n\nobject SecretConfig {\n"
    propData.forEach { (key, value) ->
        ktContent += "    const val $key = $value\n"
    }

    ktContent += "}"

    val folder = file(secretFolder)
    if (!folder.exists()) {
        folder.mkdirs()
    }

    val fileSecret = file("$secretFolder/SecretConfig.kt")
    if (!fileSecret.exists()) {
        fileSecret.createNewFile()
    }

    fileSecret.writeText(ktContent)
}

fun parseProp(content: String): Map<String, Any> {
    val propData = mutableMapOf<String, Any>()
    content.lines().forEach { line ->
        val key = line.substringBefore("=")
        val rawValue = line.substringAfter("=")
        val value: Any = when {
            rawValue == "true" || rawValue == "false" -> {
                rawValue.toBoolean()
            }

            rawValue.toIntOrNull() != null -> {
                rawValue.toInt()
            }

            rawValue.toLongOrNull() != null -> {
                rawValue.toLong()
            }

            else -> "\"$rawValue\""
        }
        propData[key] = value
    }
    return propData
}

tasks.register("generatedSecret") {
    doLast {
        generateSecret("secret.properties")
    }
}

afterEvaluate {
    tasks.getByName("generateComposeResClass").dependsOn("generatedSecret")
}