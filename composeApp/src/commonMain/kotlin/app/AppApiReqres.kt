package app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import onFailure
import onIdle
import onLoading
import onSuccess
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AppApiReqres(
    appViewModel: AppViewModel = viewModel { AppViewModel() }
) {
    val stateModel by appViewModel.stateModel.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                appViewModel.handleIntent(AppIntent.GetApi)
            }
        ) {
            Text("Test Api")
        }
        Spacer(modifier = Modifier.height(12.dp))

        with(stateModel.reqresResponseState) {
            onIdle { }
            onLoading {
                CircularProgressIndicator()
            }
            onSuccess { data ->
                Text(text = data.name)
            }
            onFailure { throwable ->
                Text(
                    text = throwable.message.orEmpty(),
                    color = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stateModel.counter.toString()
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                appViewModel.handleIntent(AppIntent.UpdateCounter)
            }
        ) {
            Text(text = "Update Counter")
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                appViewModel.restartState()
            }
        ) {
            Text(text = "Restart State")
        }

    }
}