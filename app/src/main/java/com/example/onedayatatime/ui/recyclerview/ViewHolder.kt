package com.example.onedayatatime.ui.recyclerview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * View holder base class to be used with [RecyclerViewAdapter]. Should contain only
 * references to views and view related logic.
 * @param <T> Data type of data model that is being populated into the view
</T> */
abstract class ViewHolder<T>
/**
 * Constructor
 *
 * @param view inflated list item view
 */(
    /**
     * @return the root view of the list item
     */
    val view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(model: T)

    /**
     * @return context of the list item view
     */
    val context: Context
        get() = view.context

    /**
     * Hides the item from the list without removing it.
     */
    fun hideItem(hide: Boolean) {
        if (hide) {
            view.layoutParams = ViewGroup.LayoutParams(-1, 0)
            view.visibility = View.GONE
        } else {
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            view.visibility = View.VISIBLE
        }
    }

    fun setOnItemClick(runnable: Runnable) {
        view.setOnClickListener { v: View? -> runnable.run() }
    }
}