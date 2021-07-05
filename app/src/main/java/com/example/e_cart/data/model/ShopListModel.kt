package com.example.e_cart.data.model

import java.io.Serializable

data class ShopListModel(
    var Title: String?,
    var listOfMaterial:List<MaterialModel>?
) : Serializable
