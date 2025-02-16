package com.example.comunicate2

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class LocationViewModel : ViewModel() {
    var locationState by mutableStateOf<Location?>(null)
        private set

    @SuppressLint("MissingPermission")
    fun updateLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        viewModelScope.launch {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                locationState = location
            }
        }
    }
}
