package com.example.e_cart.ui.adapter

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_cart.R
import com.example.e_cart.data.model.MaterialModel
import com.example.e_cart.databinding.ItemShopMaterialBinding
import com.example.e_cart.ui.main.MainFragment

class ShopMaterialAdapter(private var shopList:List<MaterialModel>? ) :
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
        val material = shopList?.get(position)
       val view= holder.eventBinding
        if (material != null) {
            view.checkBoxNameOfMaterial.text=material.name
            view.checkBoxNameOfMaterial.isChecked=material.isBought
            view.priceOfItemTextView.text=material.price.toString()
        }


        view.priceOfItemTextView.setOnClickListener{
            view.priceOfItemTextView.visibility= View.GONE
            view.priceOfItemEditText.setText(material?.price.toString())
            view.linearLayoutPriceEdit.visibility= View.VISIBLE
            view.floatingActionButtonNameEdit.setOnClickListener {
                view.priceOfItemTextView.text=view.priceOfItemEditText.text.toString()
                view.linearLayoutPriceEdit.visibility= View.GONE
                view.priceOfItemTextView.visibility= View.VISIBLE
            }
        }

        view.checkBoxNameOfMaterial.setOnClickListener{
            view.checkBoxNameOfMaterial.visibility= View.GONE
            view.editTextNameOfMaterial.setText(material?.price.toString())
            view.linearLayoutNameEdit.visibility= View.VISIBLE
            view.floatingActionButtonNameEdit.setOnClickListener {
                view.checkBoxNameOfMaterial.text=view.priceOfItemEditText.text.toString()
                view.linearLayoutNameEdit.visibility= View.GONE
                view.checkBoxNameOfMaterial.visibility= View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return shopList!!.size
    }

}