package entity.data

data class Place(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val coordinate: Coordinate = Coordinate(),
) {
    data class Coordinate(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
    ) {
        override fun toString(): String {
            return "$latitude,$longitude"
        }
    }
}
