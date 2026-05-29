package com.example.muzukasii

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private val repository = HotPepperRepository()

    val shopList = mutableStateListOf<Shop>()
    var currentLat by mutableStateOf<Double?>(null)
    var currentLng by mutableStateOf<Double?>(null)

    fun updateLocation(lat: Double?, lng: Double?) {
        currentLat = lat
        currentLng = lng
        loadShops()
    }

    private fun loadShops() {
        viewModelScope.launch {
            val (results, _) = repository.searchShops(
                keyword = "ラーメン",
                lat = currentLat,
                lng = currentLng,
                range = 3
            )
            shopList.clear()
            shopList.addAll(results)
        }
    }
}