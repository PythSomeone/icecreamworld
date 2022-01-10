package com.example.icecreamworld.model

import com.example.icecreamworld.data.handler.defaultImageUrl

data class Shop(
    var name: String? = "",
    var description: String? = "",
    var location: String? = "",
    val websiteLink: String? = "",
    var image: String? = defaultImageUrl,
    val menu: MutableList<Product> = mutableListOf()
)
