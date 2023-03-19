package com.example.onedayatatime.ui.recyclerview

import androidx.viewbinding.ViewBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

abstract class ListItem<T: ViewBinding> : AbstractBindingItem<T>() {
    lateinit var binding: T

    override fun bindView(binding: T, payloads: List<Any>) {
        super.bindView(binding, payloads)
        this.binding = binding
    }
}