package com.ttv.myshoppinglist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class LocationViewModel: ViewModel() {
    private val _locationData = mutableStateOf<LocationData?>(null)
    val locationData: State<LocationData?> = _locationData

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    fun updateLocation(location: LocationData) {
        _locationData.value = location
    }

    fun fetchAddress(latlng: String) {
        try {
            viewModelScope.launch {
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng,
                    BuildConfig.MAPS_API_KEY
                )
                _address.value = result.results
            }
        }catch (e: Exception){
            Log.d("Res1", "${e.cause} ${e.message}")
        }
    }
}