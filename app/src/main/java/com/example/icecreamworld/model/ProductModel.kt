package com.example.icecreamworld.model

data class Product(
    var name: String? = "",
    var price: Float? = 0.0f,
    var image: String? = "",
    val tags: MutableList<Tag> = mutableListOf()
)