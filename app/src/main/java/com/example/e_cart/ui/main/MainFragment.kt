package com.example.e_cart.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.e_cart.R
import com.example.e_cart.data.model.MaterialModel
import com.example.e_cart.data.model.MaterialModelItem
import com.example.e_cart.data.model.ShopListModel
import com.example.e_cart.databinding.FragmentMainBinding
import com.example.e_cart.ui.adapter.ItemListAdapter
import com.example.e_cart.ui.main.helperFragments.BottomSheetAddMaterialFragment
import com.example.e_cart.ui.main.helperFragments.DeleteListDialogFragment
import com.example.e_cart.ui.main.helperFragments.GetListTitleFragment
import com.example.e_cart.ui.main.viewModel.MainFragmentViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var list = ArrayList<ShopListModel>()
    private lateinit var userId: String
    private lateinit var viewModel: MainFragmentViewModel


    private lateinit var bottomSheet: BottomSheetAddMaterialFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        userId = viewModel.getUserId()

        binding.floatingActionButton.setOnClickListener {
            showDialog()
        }
    }

    private lateinit var dialog: GetListTitleFragment
    private val deleteListDialog = DeleteListDialogFragment()

    private fun showDialog() {
        dialog = GetListTitleFragment(onClicked = { title ->
            if (title != null) {
                val shopItem = ShopListModel(title.toString(), ArrayList())
                viewModel.addListToDatabase(title.toString())
                list.add(shopItem)
            } else {
                Toast.makeText(context, "Lutfen bir baslik giriniz", Toast.LENGTH_SHORT).show()//????
            }
        })
        dialog.show(childFragmentManager, "dialog")
    }

    private fun setViewModel() {
        viewModel = MainFragmentViewModel(context) { list ->
            binding.MainScreenRecyclerView.adapter = ItemListAdapter(requireContext(), list, { title, position ->
                setBottomSheet(title, position)
                bottomSheet.show(requireActivity().supportFragmentManager, "bottomSheetFragment")
            }, {
                deleteListDialog.show(requireActivity().supportFragmentManager, "delete_list_dialog")
            })
        }
    }

    private fun setBottomSheet(title: String, position: Int) {
        bottomSheet = BottomSheetAddMaterialFragment { name, price ->
            viewModel.addMaterialToDatabase(title, name, price)
            bottomSheet.dismiss()
            bottomSheet.show(requireActivity().supportFragmentManager, "bottomSheetFragment")
            list[position].listOfMaterial?.add(MaterialModel(MaterialModelItem(name, price)))
            binding.MainScreenRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }
}
