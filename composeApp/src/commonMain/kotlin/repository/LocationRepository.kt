package repository

import base.BaseRepository
import base.State
import entity.PlaceMapper
import entity.data.Place
import entity.response.PlaceResponse
import kotlinx.coroutines.flow.Flow
import org.onedev.kmp.SecretConfig

class LocationRepository: BaseRepository() {
    private val apiKey = SecretConfig.HERE_API_KEY

    fun searchLocation(query: String, coordinate: Place.Coordinate): Flow<State<List<Place>>> {
        return suspend {
            getHttpResponse("https://discover.search.hereapi.com/v1/discover?at=$coordinate&limit=1&q=$query&in=countryCode:IDN&apiKey=$apiKey")
        }.reduce<PlaceResponse, List<Place>> { response ->
            val data = PlaceMapper.mapResponseToPlaces(response)
            State.Success(data)
        }
    }

    fun reversedLocation(coordinate: Place.Coordinate): Flow<State<List<Place>>> {
        return suspend {
            getHttpResponse("https://revgeocode.search.hereapi.com/v1/revgeocode?at=$coordinate&limit=3&lang=id-ID&apiKey=$apiKey")
        }.reduce<PlaceResponse, List<Place>> { response ->
            val data = PlaceMapper.mapResponseToPlaces(response)
            State.Success(data)
        }
    }
}