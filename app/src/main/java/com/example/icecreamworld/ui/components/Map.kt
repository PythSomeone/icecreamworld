package com.example.icecreamworld.ui.components

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.icecreamworld.data.repository.ShopRepository
import com.example.icecreamworld.model.Shop
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GoogleMaps(currentLocation: Location) {
    val mapView = rememberMapViewWithLifeCycle()
    val latlng =
        remember { mutableStateOf("lat : ${currentLocation.latitude},lang: ${currentLocation.longitude}") }
    val geocoder = Geocoder(LocalContext.current)
    Box() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AndroidView(
                { mapView }
            ) { mapView ->
                CoroutineScope(Dispatchers.Main).launch {
                    mapView.getMapAsync {
                        it.mapType = 1
                        it.uiSettings.isZoomControlsEnabled = true
                        val currentLatLng =
                            LatLng(currentLocation.latitude, currentLocation.longitude)
                        it.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                        it.setOnCameraIdleListener {
                            it.clear()
                            it.addMarker(
                                MarkerOptions()
                                    .position(currentLatLng)
                                    .title("Your position")
                            )
                            latlng.value = ("lat: ${it.cameraPosition.target.latitude}," +
                                    " lang: ${it.cameraPosition.target.longitude}")
                            ShopRepository.data.value.forEach { snapshot ->
                                val currentAddress = geocoder.getFromLocationName(snapshot.getValue<Shop>()?.location!!,1)
                                if (currentAddress.isNotEmpty()){
                                    val specifiedLatLng = LatLng(currentAddress[0].latitude,currentAddress[0].longitude)
                                    it.addMarker(
                                        MarkerOptions()
                                            .position(specifiedLatLng)
                                            .title(snapshot.getValue<Shop>()?.name)
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun rememberMapViewWithLifeCycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = com.google.maps.android.ktx.R.id.map_frame
        }
    }
    val lifeCycleObserver = rememberMapLifecycleObserver(mapView)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifeCycle) {
        lifeCycle.addObserver(lifeCycleObserver)
        onDispose {
            lifeCycle.removeObserver(lifeCycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }
