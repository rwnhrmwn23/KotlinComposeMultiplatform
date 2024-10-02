package app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import navigation.LocalNavigationController
import navigation.Routers
import org.jetbrains.compose.ui.tooling.preview.Preview
import screen.Screen1
import screen.Screen2
import screen.Screen3

@Preview
@Composable
fun AppNavigationComponent() {
    MaterialTheme {

        val navigationController = rememberNavController()

        CompositionLocalProvider(
            LocalNavigationController provides navigationController
        ) {
            NavHost(
                navController = navigationController,
                startDestination = Routers.SCREEN_1
            ) {
                composable(
                    route = Routers.SCREEN_1
                ) {
                    Screen1()
                }

                composable(
                    route = Routers.SCREEN_2
                ) {
                    val name = it.arguments?.getString("name").orEmpty()
                    Screen2(name)
                }

                composable(
                    route = Routers.SCREEN_3
                ) {
                    Screen3()
                }
            }
        }
    }
}