package com.example.muzukasii

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = HotPepperRepository()

    var text by mutableStateOf("")
        private set

    val shopList = mutableStateListOf<Shop>()

    var totalAvailable by mutableIntStateOf(0)
        private set

    private var nextStart = 1
    private var currentLat: Double? = null
    private var currentLng: Double? = null

    val rangeOptions = listOf(
        "すべて" to null,
        "300m" to 1,
        "500m" to 2,
        "1km" to 3,
        "2km" to 4,
        "3km" to 5
    )
    var selectedRangeIndex by mutableIntStateOf(0)
        private set

    fun onTextChange(newText: String) {
        text = newText
    }

    fun updateLocation(lat: Double?, lng: Double?) {
        currentLat = lat
        currentLng = lng
    }

    fun onRangeSelected(index: Int) {
        selectedRangeIndex = index
        search()
    }

    fun search() {
        viewModelScope.launch {
            val range = rangeOptions[selectedRangeIndex].second
            val (results, available) = repository.searchShops(
                keyword = text.ifBlank { "ラーメン" },
                start = 1,
                lat = if (range != null) currentLat else null,
                lng = if (range != null) currentLng else null,
                range = range
            )
            shopList.clear()
            shopList.addAll(results)
            nextStart = 11
            totalAvailable = available
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            val range = rangeOptions[selectedRangeIndex].second
            val (newShops, _) = repository.searchShops(
                keyword = text.ifBlank { "ラーメン" },
                start = nextStart,
                lat = if (range != null) currentLat else null,
                lng = if (range != null) currentLng else null,
                range = range
            )
            if (newShops.isNotEmpty()) {
                shopList.addAll(newShops)
                nextStart += 10
            }
        }
    }
}