package com.example.icecreamworld.model

import com.example.icecreamworld.data.handler.defaultImageUrl

data class Product(
    var name: String? = "",
    var price: Float? = 0.0f,
    var image: String? = defaultImageUrl,
    val tags: MutableList<Tag> = mutableListOf()
)