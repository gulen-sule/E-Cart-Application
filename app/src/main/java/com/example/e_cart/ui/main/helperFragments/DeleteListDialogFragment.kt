package com.example.e_cart.ui.main.helperFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.e_cart.R
import com.example.e_cart.databinding.DialogFragmentDeleteListBinding

class DeleteListDialogFragment: DialogFragment() {
    private lateinit var binding: DialogFragmentDeleteListBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_delete_list,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}