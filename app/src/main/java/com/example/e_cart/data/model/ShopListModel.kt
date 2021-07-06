package com.example.e_cart.data.model

import java.io.Serializable

data class ShopListModel(
    var title: String?=null,
    var listOfMaterial:List<MaterialModel>?=null
) : Serializable
