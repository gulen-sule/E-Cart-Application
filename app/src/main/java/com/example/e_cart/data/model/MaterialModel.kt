package com.example.e_cart.data.model

data class MaterialModel(
    val material: MaterialModelItem? = null,
)

data class MaterialModelItem(
    val name: String? = null,
    var price: Double? = 0.0,
    var isBought: Boolean = false
)

