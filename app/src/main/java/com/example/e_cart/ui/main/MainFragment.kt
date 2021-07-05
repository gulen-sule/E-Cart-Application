package com.example.e_cart.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.e_cart.R
import com.example.e_cart.data.model.MaterialModel
import com.example.e_cart.data.model.ShopListModel
import com.example.e_cart.databinding.FragmentMainBinding
import com.example.e_cart.databinding.ItemListBinding
import com.example.e_cart.ui.adapter.ItemListAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val list = ArrayList<ShopListModel>()
    private lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       database= Firebase.database.reference


        binding.MainScreenRecyclerView.adapter = ItemListAdapter(list)
        binding.floatingActionButton.setOnClickListener {
            binding.layoutGetListTitle.layoutGetListTitle.visibility = View.VISIBLE

            binding.layoutGetListTitle.floatingActionButton2.setOnClickListener {
                val title = binding.layoutGetListTitle.editText.text
                if (title != null) {
                    val shopItem=ShopListModel(title.toString(),ArrayList<MaterialModel>())
                    database.child(title.toString()).setValue(shopItem)
                    list.add(shopItem)
                    binding.layoutGetListTitle.layoutGetListTitle.visibility = View.GONE
                }
            }
        }
    }
}