package com.example.e_cart.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_cart.R
import com.example.e_cart.data.model.MaterialModel
import com.example.e_cart.data.model.ShopListModel
import com.example.e_cart.databinding.ItemListBinding
import com.example.e_cart.ui.main.MainActivity
import com.example.e_cart.ui.main.MainFragment

class ItemListAdapter(private var shopList: List<ShopListModel>) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
    val list = ArrayList<MaterialModel>()

    inner class ViewHolder(itemView: ItemListBinding) : RecyclerView.ViewHolder(itemView.root) {
        var eventBinding: ItemListBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val depremBinding: ItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_list, parent, false)
        return ViewHolder(depremBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = shopList[position]
        holder.eventBinding.ShopListTitle.text = listItem.Title
        holder.eventBinding.ShopListRecyclerView.adapter = ShopMaterialAdapter(listItem.listOfMaterial as ArrayList?)
        holder.eventBinding.floatingActionButtonShopList.setOnClickListener {
        list.add(MaterialModel(name = "yumurta",price = 20.0,false))
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

}