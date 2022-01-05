package com.example.icecreamworld.model

data class Shop(
    var name: String? = "",
    var description: String? = "",
    var location: String? = "",
    val websiteLink: String? = "",
    var image: String? = "",
    val menu: MutableList<Product> = mutableListOf(),
)
