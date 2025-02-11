package features.reversed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import entity.data.Place
import onFailure
import onLoading
import onSuccess

@Composable
fun ReversedLocationScreen(
    viewModel: ReversedLocationViewModel = viewModel { ReversedLocationViewModel() }
) {
    val model by viewModel.stateModel.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = model.coordinate.latitude.toString(),
            onValueChange = {
                viewModel.handleIntent(
                    ReversedLocationIntent.Latitude(it.toDoubleOrNull() ?: 0.0)
                )
            },
            label = {
                Text("Latitude")
            }
        )
        Spacer(Modifier.height(12.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = model.coordinate.longitude.toString(),
            onValueChange = {
                viewModel.handleIntent(
                    ReversedLocationIntent.Longitude(it.toDoubleOrNull() ?: 0.0)
                )
            },
            label = {
                Text("Longitude")
            }
        )
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = {
                viewModel.handleIntent(
                    ReversedLocationIntent.GetPlaces
                )
            },
            enabled = model.isReversedButtonEnable
        ) {
            Text("Get Places")
        }
        Spacer(Modifier.height(12.dp))
        with(model.placeState) {
            onLoading {
                CircularProgressIndicator()
            }
            onFailure {
                Text(it.message.toString())
            }
            onSuccess { places ->
                for (place in places) {
                    PlaceContent(place)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun PlaceContent(place: Place) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = place.name,
                style = TextStyle.Default.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = place.address,
                style = TextStyle.Default
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = place.coordinate.toString(),
                style = TextStyle.Default
            )
        }
    }
}