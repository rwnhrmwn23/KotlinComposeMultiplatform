package navigation

import androidx.compose.runtime.staticCompositionLocalOf

interface Navigator {
    fun navigate(navTarget: NavTarget)
    fun back()
}

val LocalNavigator = staticCompositionLocalOf<Navigator> { error("Navigator not Provided") }