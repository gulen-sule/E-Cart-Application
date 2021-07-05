package com.example.e_cart.ui.adapter

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_cart.R
import com.example.e_cart.data.model.MaterialModel
import com.example.e_cart.databinding.ItemShopMaterialBinding

class ShopMaterialAdapter(private val context: Context,private var shopList:List<MaterialModel> ) :
    RecyclerView.Adapter<ShopMaterialAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: ItemShopMaterialBinding) : RecyclerView.ViewHolder(itemView.root) {
        var eventBinding: ItemShopMaterialBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val depremBinding: ItemShopMaterialBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_shop_material, parent, false)
        return ViewHolder(depremBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val material = shopList[position]
        holder.eventBinding.checkBoxNameOfMaterial.text=material.name
        holder.eventBinding.checkBoxNameOfMaterial.isChecked=material.isBought
        holder.eventBinding.priceOfItemEditText.text= Editable.Factory.getInstance().newEditable(material.price.toString())
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

}