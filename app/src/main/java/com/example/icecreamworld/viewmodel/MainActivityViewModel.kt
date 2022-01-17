package com.example.icecreamworld.viewmodel

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private var _location = mutableStateOf(Location(""))
    var location: MutableState<Location> = _location
}