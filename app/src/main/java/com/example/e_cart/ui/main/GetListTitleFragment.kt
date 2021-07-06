package com.example.e_cart.ui.main

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.e_cart.R
import com.example.e_cart.databinding.GetListTitleBinding

class GetListTitleFragment(val onClicked: (Editable?) -> Unit) : DialogFragment() {

    private lateinit var binding: GetListTitleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.get_list_title, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButtonGetTitle.setOnClickListener {
            val title = binding.editText.text
            onClicked(title)
            dismiss()
        }
    }
}