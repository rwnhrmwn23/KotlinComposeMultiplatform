package features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.LocalNavigator
import navigation.NavTarget

@Composable
fun MainScreen() {
    val navigator = LocalNavigator.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                navigator.navigate(NavTarget.SearchLocationScreen)
            },
        ) {
            Text("Search Location")
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = {
                navigator.navigate(NavTarget.ReversedLocationScreen)
            },
        ) {
            Text("Reversed Location")
        }
    }
}