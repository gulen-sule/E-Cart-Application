package com.example.e_cart.ui.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_cart.R
import com.example.e_cart.data.model.MaterialModel
import com.example.e_cart.databinding.ItemShopMaterialBinding

class ShopMaterialAdapter(private var shopList: List<MaterialModel>?) :
    RecyclerView.Adapter<ShopMaterialAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: ItemShopMaterialBinding) : RecyclerView.ViewHolder(itemView.root) {
        var eventBinding: ItemShopMaterialBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val depremBinding: ItemShopMaterialBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_shop_material, parent, false)
        return ViewHolder(depremBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val material = shopList?.get(position)?.material
        val view = holder.eventBinding
        if (material != null) {
            view.nameOfMaterialTextView.text = material.name
            view.checkBoxNameOfMaterial.isChecked = material.isBought
            view.priceOfItemTextView.text = material.price.toString()
        }

        setTextViewFocusable(view.priceOfItemTextView)

    }

    override fun getItemCount(): Int {
        return shopList!!.size
    }

    private fun setTextViewFocusable(view: TextView) {
        view.requestFocus()
        view.isFocusableInTouchMode = true
        view.setEditableFactory(Editable.Factory())
    }

}