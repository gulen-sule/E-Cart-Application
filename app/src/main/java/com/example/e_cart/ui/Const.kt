package com.example.e_cart.ui

import com.google.firebase.database.FirebaseDatabase

object Const {
    const val shopListsCollectionName = "listBasket"
    const val materialList = "listOfMaterial"
    val firebaseReferenceUserList = FirebaseDatabase.getInstance().reference.child("userLists")
}