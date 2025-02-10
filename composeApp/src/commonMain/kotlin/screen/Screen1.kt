package screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import navigation.LocalNavigator
import navigation.NavTarget
import onFailure
import onIdle
import onLoading
import onSuccess

@Composable
fun Screen1(
    viewModel: Screen1ViewModel = viewModel { Screen1ViewModel() }
) {

    val modelState by viewModel.stateModel.collectAsState()
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

        Spacer(Modifier.height(12.dp))

        with(modelState.reqresState) {
            onIdle {
                Button(
                    onClick = {
                        viewModel.handleIntent(Screen1Intent.GetUser)
                    }
                ) {
                    Text("Get User")
                }
            }
            onLoading {
                CircularProgressIndicator()
            }
            onSuccess {
                Text(text = it.name)
            }
            onFailure {
                Button(
                    onClick = {
                        viewModel.handleIntent(Screen1Intent.GetUser)
                    }
                ) {
                    Text("Try Again")
                }
            }
        }
    }
}