package com.example.onedayatatime.ui.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.onedayatatime.R
import com.example.onedayatatime.databinding.ListitemAddButtonBinding
import com.example.onedayatatime.ui.recyclerview.ListItem


abstract class AddTaskItem : ListItem<ListitemAddButtonBinding>() {
    override val type: Int get() = R.id.listitem_add_button

    override fun bindView(binding: ListitemAddButtonBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.root.setOnClickListener {
            onClick()
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ListitemAddButtonBinding {
        return ListitemAddButtonBinding.inflate(inflater, parent, false)
    }

    abstract fun onClick()
}