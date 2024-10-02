package screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.LocalNavigationController
import navigation.LocalNavigator
import navigation.NavTarget
import navigation.Routers

@Composable
fun Screen1() {

    val navigator = LocalNavigator.current
    var textFieldName by remember { mutableStateOf("") }

    Column {
        Button(
            onClick = {

            }
        ) {
            Text("Back")
        }

        Spacer(Modifier.height(12.dp))

        Text("Text Screen 1")

        Spacer(Modifier.height(12.dp))

        TextField(
            value = textFieldName,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                textFieldName = it
            }
        )

        Button(
            onClick = {
                navigator.navigate(
                    NavTarget.Screen2(textFieldName)
                )
            }
        ) {
            Text("Navigate To Screen 2")
        }
    }
}