package com.example.icecreamworld.model

import com.google.android.libraries.maps.model.LatLng

data class Shop(
    var name: String? = "",
    val description: String? = "",
    var location: String? = "",
    val websiteLink: String? = "",
    var image: String? = "",
    val menu: MutableList<Product> = mutableListOf(),
)
