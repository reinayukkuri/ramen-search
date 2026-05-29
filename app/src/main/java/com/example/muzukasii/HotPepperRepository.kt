package com.example.muzukasii

class HotPepperRepository {
    private val client = HotPepperClient()

    suspend fun searchShops(
        keyword: String,
        start: Int = 1,
        lat: Double? = null,
        lng: Double? = null,
        range: Int? = null
    ): Pair<List<Shop>, Int> {
        return client.searchShops(
            keyword = keyword,
            start = start,
            lat = lat,
            lng = lng,
            range = range
        )
    }
}