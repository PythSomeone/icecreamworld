package com.example.icecreamworld.model

import com.google.android.libraries.maps.model.LatLng

data class Shop(
    var name: String? = "",
    var description: String? = "",
    var location: String? = "",
    var websiteLink: String? = "",
    var image: String? = "",
    val menu: MutableList<Product> = mutableListOf(),
)
