package com.example.icecreamworld

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.example.icecreamworld.navigation.NavigationPage
import com.example.icecreamworld.ui.theme.IceCreamWorldTheme
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.Exception


class MainActivity : ComponentActivity() {
//    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        super.onCreate(savedInstanceState)
//        UserHandler.userListener()
//        PostHandler.postListener()
        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
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
            }
        }
    }
}