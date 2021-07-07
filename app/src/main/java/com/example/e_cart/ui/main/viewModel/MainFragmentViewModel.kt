package com.example.e_cart.ui.main.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.e_cart.data.model.MaterialModel
import com.example.e_cart.data.model.MaterialModelItem
import com.example.e_cart.data.model.ShopListModel
import com.example.e_cart.ui.Const
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MainFragmentViewModel(val context: Context?,val finished:(ArrayList<ShopListModel>)->Unit) : ViewModel() {
    private lateinit var userId: String

    init {
        val userShared = context?.getSharedPreferences("e_cart_preferences", Context.MODE_PRIVATE)
        userId = userShared?.getString("ID", "idx404NOT_FOUNDx")!!
        readDataFromRealtime()
    }

    fun getUserId() = userId

    private fun readDataFromRealtime() {
        val list = ArrayList<ShopListModel>()
        Const.firebaseReferenceUserList.child(userId).child(Const.shopListsCollectionName).get()
            .addOnSuccessListener {
                it.children.forEach { data ->
                    if (data.hasChildren()) {
                        val shopList = ShopListModel(title = data.key)
                        shopList.listOfMaterial=ArrayList()
                        data.child(Const.materialList).children.forEach { dataSnapshot ->
                            val materialModelItem = dataSnapshot.getValue<MaterialModelItem>()
                            shopList.listOfMaterial?.add(MaterialModel(material = materialModelItem))
                        }
                        list.add(shopList)
                    } else {
                        list.add(ShopListModel(title = data.key!!))
                    }
                }
                finished(list)
            }
            .addOnFailureListener {
                Log.w("MainFragment_TAG", it.stackTraceToString())
            }
    }

    fun addMaterialToDatabase(title: String, itemName: String, itempPrice: Double) {
        val database = Const.firebaseReferenceUserList.child(userId).child(Const.shopListsCollectionName).child(title).child(Const.materialList)
        database.push().setValue(MaterialModelItem(itemName, itempPrice, false))

    }

    fun addListToDatabase(titleOfList: String) {
        Const.firebaseReferenceUserList.child(userId)
            .child(Const.shopListsCollectionName)
            .child(titleOfList)
            .setValue(ShopListModel(title = titleOfList))
    }

    fun adbul() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("PostListenerErrorTAG", "loadPost:onCancelled", error.toException())
            }
        }

        Const.firebaseReferenceUserList.addValueEventListener(postListener)
    }


}