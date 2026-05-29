package com.example.muzukasii

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class HotPepperClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                },
                contentType = ContentType.Any
            )
        }
    }

    suspend fun searchShops(
        keyword: String,
        start: Int = 1,
        lat: Double? = null,
        lng: Double? = null,
        range: Int? = null
    ): Pair<List<Shop>, Int> {


        val apiKey = ""
        val url = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/"


        val response = client.get(url) {
            parameter("key", apiKey)
            parameter("format", "json")
            parameter("genre", "G013")
            parameter("count", 10)
            parameter("start", start)

            //nullの場合APIに送らないようにするために
            if (lat != null) parameter("lat", lat)
            if (lng != null) parameter("lng", lng)
            if (range != null) parameter("range", range)
            if (keyword.isNotBlank()) parameter("keyword", keyword)

        }

        val data = response.body<HotPepperResponse>()

        val shops = data.results.shop ?: emptyList()
        val available = data.results.resultsAvailable ?: 0
        return Pair(shops, available)


    }
}