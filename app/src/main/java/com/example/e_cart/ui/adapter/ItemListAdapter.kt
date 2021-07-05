package com.example.e_cart.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_cart.R
import com.example.e_cart.data.model.ShopListModel
import com.example.e_cart.databinding.ItemListBinding

class ItemListAdapter(private var shopList: List<ShopListModel>) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: ItemListBinding) : RecyclerView.ViewHolder(itemView.root) {
        var eventBinding: ItemListBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val depremBinding: ItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_list, parent, false)
        return ViewHolder(depremBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = shopList[position]
        holder.eventBinding.ShopListTitle.text = list.Title

    }

    override fun getItemCount(): Int {
        return shopList.size
    }

}