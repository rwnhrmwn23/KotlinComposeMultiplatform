package app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AppNavigationAppyx(
    viewModel: AppViewModel = viewModel { AppViewModel() }
) {
    MaterialTheme {

    }
}