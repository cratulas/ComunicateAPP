package com.example.comunicate2

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(navController: NavController, viewModel: LocationViewModel = viewModel()) {
    val context = LocalContext.current
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }
    }

    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = true) }

    val properties = remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = locationPermissionState.status.isGranted,
                mapType = MapType.NORMAL
            )
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 10f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = properties.value,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState,
            onMapLoaded = { viewModel.updateLocation(context) }
        ) {
            viewModel.locationState?.let { location ->
                val userLatLng = LatLng(location.latitude, location.longitude)
                Marker(
                    state = MarkerState(position = userLatLng),
                    title = "Ubicación Actual"
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    showCityName(context, viewModel)
                    moveCameraToLocation(viewModel, cameraPositionState)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar Ubicación")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Volver")
            }
        }
    }
}


// Funciones

// Mover Mapa
fun moveCameraToLocation(viewModel: LocationViewModel, cameraPositionState: CameraPositionState) {
    val location = viewModel.locationState
    if (location != null) {
        val userLatLng = LatLng(location.latitude, location.longitude)
        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
    }
}

// Mostrar nombre ciudad al entrar al mapa
@SuppressLint("MissingPermission")
fun showCityName(context: Context, viewModel: LocationViewModel) {
    val location = viewModel.locationState
    if (location != null) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val cityName = addresses[0].locality ?: "Ubicación desconocida"
                Toast.makeText(context, "Te encuentras en: $cityName", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "No se pudo obtener la ciudad", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error al obtener la ciudad", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "Ubicación no disponible", Toast.LENGTH_SHORT).show()
    }
}
