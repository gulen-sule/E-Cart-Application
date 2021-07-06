package com.example.e_cart.ui.adapter

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.e_cart.R
import com.example.e_cart.data.model.MaterialModel
import com.example.e_cart.data.model.ShopListModel
import com.example.e_cart.databinding.ItemListBinding
import com.example.e_cart.ui.Const
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.coroutineContext

class ItemListAdapter(val context: Context, private var shopList: List<ShopListModel>) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: ItemListBinding) : RecyclerView.ViewHolder(itemView.root) {
        var eventBinding: ItemListBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val depremBinding: ItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_list, parent, false)
        return ViewHolder(depremBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = ArrayList<MaterialModel>()
        val listItem = shopList[position]
        Log.d("ListItemTag", listItem.toString())
        holder.eventBinding.ShopListTitle.text = listItem.title
        val shopMaterialAdapter: ShopMaterialAdapter
        if (listItem.listOfMaterial != null) {
            shopMaterialAdapter = ShopMaterialAdapter(listItem.listOfMaterial as ArrayList)
            holder.eventBinding.ShopListRecyclerView.adapter = shopMaterialAdapter
        } else {
            shopMaterialAdapter = ShopMaterialAdapter(list)
            holder.eventBinding.ShopListRecyclerView.adapter = shopMaterialAdapter
        }

        holder.eventBinding.floatingActionButtonShopList.setOnClickListener {
            val data = MaterialModel(name = "yumurta", price = 20.0, false)
            list.add(data)
            addToDatabase(listItem.title.toString(), data)
            shopMaterialAdapter.notifyDataSetChanged()
            holder.eventBinding.ShopListRecyclerView.adapter?.notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    private fun addToDatabase(title: String, dataModel: MaterialModel) {
        val userShared = context.getSharedPreferences("e_cart_preferences", Context.MODE_PRIVATE)
        val userId = userShared?.getString("ID", "idx404NOT_FOUNDx")
        val database =
            FirebaseDatabase.getInstance().reference.child(Const.firstCollectionName).child(userId.toString()).child(Const.shopListsCollectionName)
        val look = database.child(title).child(Const.materialList).child(dataModel.name).setValue(dataModel)
        Log.d("isSuccesfulTAG", look.isSuccessful.toString())

    }

    private fun addPostEventListener(postReference: DatabaseReference) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // val post = dataSnapshot.getValue<Post>()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ItemListAdapterTAG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        postReference.addValueEventListener(postListener)
        // [END post_value_event_listener]
    }

}