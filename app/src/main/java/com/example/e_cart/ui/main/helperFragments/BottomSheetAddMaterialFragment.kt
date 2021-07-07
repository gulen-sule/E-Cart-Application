package com.example.e_cart.ui.main.helperFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.e_cart.R
import com.example.e_cart.databinding.FragmentBottomSheetAddMaterialBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetAddMaterialFragment(private val onAddButtonClicked: (String, Double) -> Unit,) :
    BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetAddMaterialBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_add_material, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val behavior = BottomSheetBehavior.from(binding.bottomSheetLinearLayout)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.addMaterialButton.setOnClickListener {
            val itemName = binding.productNameBottomSheetEditText.text.toString()
            val itemPrice = binding.priceBottomSheetEditText.text.toString().toDouble()
            binding.productNameBottomSheetEditText.text=null
            binding.priceBottomSheetEditText.text=null
            onAddButtonClicked(itemName, itemPrice)
        }
        binding.floatingActionButtonCloseSheet.setOnClickListener {
            dismiss()
        }
    }


}