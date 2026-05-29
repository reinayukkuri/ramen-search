package com.example.muzukasii

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//APIのレスポンス受け皿
@Serializable
data class HotPepperResponse(
    val results: Results
)

//resultsの中身を受け取る器
@Serializable
data class Results(
    @SerialName("shop")
    val shop: List<Shop>? = emptyList(),

    @SerialName("api_version") val apiVersion: String? = null,
    @SerialName("results_available") val resultsAvailable: Int? = null

)

//ショップの各データ取得するのに必要
@Serializable
data class Shop(
    val name: String,
    val address: String,
    val open: String? = null,
    val access: String? = null,
    val urls: Urls? = null,
    @SerialName("photo")
    val photo: Photo? = null
)

//? = nullをつけてるのは、例外処理。なかった場合処理落ちするから書いてる
//APIから画像取得するために必要
@Serializable
data class Photo(
    val pc: PhotoUrl? = null,
    val mobile: PhotoUrl? = null,
)

//画像サイズ
@Serializable
data class PhotoUrl(
    val s: String? = null,
    val l: String? = null
)

@Serializable
data class Urls(
    val pc: String? = null
)

