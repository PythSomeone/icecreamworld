package com.example.icecreamworld

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.icecreamworld.data.repository.ShopFormRepository
import com.example.icecreamworld.data.repository.ShopRepository
import com.example.icecreamworld.data.repository.TagRepository
import com.example.icecreamworld.navigation.NavigationPage
import com.example.icecreamworld.ui.theme.IceCreamWorldTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private var listenersInitialized = false
private fun initializeListeners() {
    ShopRepository.listenToChanges()
    ShopFormRepository.listenToChanges()
    TagRepository.listenToChanges()
    Log.d("Listeners", "Listeners initialized")
    listenersInitialized = true
}

class MainActivity : ComponentActivity() {
//    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        super.onCreate(savedInstanceState)
        if (listenersInitialized.not())
            initializeListeners()
        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    101
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val location = fusedLocationClient.lastLocation

        setContent {
            IceCreamWorldTheme {
                NavigationPage(location)
//                EditShopScreen(navController = rememberNavController(), shopId = null)
            }
        }
    }
}