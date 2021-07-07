package com.example.e_cart.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_cart.R
import com.example.e_cart.data.model.MaterialModel
import com.example.e_cart.data.model.ShopListModel
import com.example.e_cart.databinding.ItemListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ItemListAdapter(
    val context: Context,
    private var shopList: List<ShopListModel>,
    private val addItemClicked: (String, Int) -> Unit,
    private val listDeleteClicked:()->Unit
) :
    RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
    private val list = ArrayList<MaterialModel>()
    private lateinit var shopMaterialAdapter: ShopMaterialAdapter


    inner class ViewHolder(itemView: ItemListBinding) : RecyclerView.ViewHolder(itemView.root) {
        var eventBinding: ItemListBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val depremBinding: ItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_list, parent, false)
        return ViewHolder(depremBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = shopList[position]
        Log.d("ListItemTag", listItem.toString())
        holder.eventBinding.ShopListTitle.text = listItem.title

        shopMaterialAdapter = if (listItem.listOfMaterial != null) {
            ShopMaterialAdapter(listItem.listOfMaterial)
        } else {
            ShopMaterialAdapter(list)
        }
        holder.eventBinding.ShopListRecyclerView.adapter = shopMaterialAdapter

        holder.eventBinding.floatingActionButtonShopList.setOnClickListener {
            addItemClicked(listItem.title.toString(), position).also {
                shopMaterialAdapter.notifyDataSetChanged()
                holder.eventBinding.ShopListRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

        holder.eventBinding.ShopListTitle.setOnLongClickListener { view ->
            listDeleteClicked()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
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