package screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.LocalNavigationController
import navigation.LocalNavigator
import navigation.NavTarget
import navigation.Routers

@Composable
fun Screen2(name: String) {

    val navigator = LocalNavigator.current

    Column {
        Button(
            onClick = {
                navigator.back()
            }
        ) {
            Text("Back")
        }

        Spacer(Modifier.height(12.dp))

        Text("Text Screen 2\nName : $name")

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                navigator.navigate(
                    NavTarget.Screen3
                )
            }
        ) {
            Text("Navigate To Screen 3")
        }
    }
}