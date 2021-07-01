package com.example.e_cart.data.model

import java.io.Serializable
import java.io.SerializablePermission

data class loginModel(
    private var  name:String?,
    private var mail:String?,
    private var password:String)

: Serializable