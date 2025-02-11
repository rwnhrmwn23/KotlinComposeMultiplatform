package features

import androidx.lifecycle.viewModelScope
import base.BaseViewModel
import base.State
import entity.data.Place
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import repository.LocationRepository

data class SearchLocationModel(
    val query: String = "", val placeState: State<List<Place>> = State.Idle
)

sealed class SearchLocationIntent {
    data class Query(val query: String) : SearchLocationIntent()
    data object Search : SearchLocationIntent()
}

class SearchLocationViewModel : BaseViewModel<SearchLocationModel, SearchLocationIntent>(
    SearchLocationModel()
) {
    private val locationRepository = LocationRepository()

    override fun handleIntent(appIntent: SearchLocationIntent) {
        when (appIntent) {
            is SearchLocationIntent.Query -> {
                sendQuery(appIntent.query)
            }

            is SearchLocationIntent.Search -> {
                searchLocation()
            }
        }
    }

    private fun sendQuery(query: String) {
        updateModel { model ->
            model.copy(
                query = query
            )
        }
    }

    private fun searchLocation() = viewModelScope.launch {
        val query = stateModel.value.query
        locationRepository.searchLocation(
            query = query,
            coordinate = Place.Coordinate(
                latitude = -6.21807449449637,
                longitude = 106.94158151833594
            )
        )
            .stateIn(this)
            .collectLatest {
                updateModel { model ->
                    model.copy(
                        placeState = it
                    )
                }
            }
    }
}