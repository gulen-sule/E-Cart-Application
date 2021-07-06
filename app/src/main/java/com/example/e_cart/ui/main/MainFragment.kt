package com.example.e_cart.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.e_cart.R
import com.example.e_cart.data.model.MaterialModel
import com.example.e_cart.data.model.ShopListModel
import com.example.e_cart.databinding.FragmentMainBinding
import com.example.e_cart.ui.Const
import com.example.e_cart.ui.adapter.ItemListAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var list = ArrayList<ShopListModel>()
    private lateinit var database: DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userShared = context?.getSharedPreferences("e_cart_preferences", Context.MODE_PRIVATE)
        val userId = userShared?.getString("ID", "idx404NOT_FOUNDx")
        database = FirebaseDatabase.getInstance().reference
        database.child(Const.firstCollectionName).child(userId.toString()).child(Const.shopListsCollectionName).get().addOnSuccessListener {
            it.children.forEach { data ->
                Log.i("eachForTAG", data.getValue(ShopListModel::class.java).toString())
                if(data.hasChildren())
                list.add(data.getValue(ShopListModel::class.java) as ShopListModel)
            }.also {
                binding.MainScreenRecyclerView.adapter = ItemListAdapter(requireContext(),list)
                binding.MainScreenRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
            .addOnFailureListener {
                Log.d("MainFragment_TAG", it.stackTraceToString())
            }
        binding.floatingActionButton.setOnClickListener {
            showDialog(userId)
        }
    }

    private lateinit var dialog: GetListTitleFragment

    private fun showDialog(id: String?) {
        dialog = GetListTitleFragment(onClicked = { title ->
            if (title != null) {
                val shopItem = ShopListModel(title.toString(), ArrayList())
                database.child(Const.firstCollectionName).child(id!!).child(Const.shopListsCollectionName).child(title.toString()).setValue(shopItem)
                list.add(shopItem)
            } else {
                Toast.makeText(context, "Lutfen bir baslik giriniz", Toast.LENGTH_SHORT).show()//????
            }
        })

        dialog.show(childFragmentManager, "dialog")
    }
}