package com.example.onedayatatime.ui.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/**
 * Simplified extension of {@link RecyclerView.Adapter} for creating recyclable lists.
 * To use, onCreateViewHolder should be overridden
 * @param <T> Data holding type for the individual items in the list.
 *           If supporting multiple viewTypes use {@link ListItem}
 * @param <V> ViewHolder type for the individual list items.
 *           If supporting multiple viewTypes use {@link MPostRecyclerViewAdapter.ViewHolder<ListItem>}
 */
abstract class RecyclerViewAdapter <T, V : ViewHolder<T>>
/**
 * Constructor
 *
 * @param context Context object of calling activity/fragment/view
 */(context: Context?) : RecyclerView.Adapter<V>() {

    private var listData: List<T>
    protected var inflater: LayoutInflater

    init {
        listData = ArrayList()
        inflater = LayoutInflater.from(context)
    }

    /** Override and return a view holder */
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V

    /** Automatically binds view holder to list item data model*/
    override fun onBindViewHolder(holder: V, position: Int) {
        holder.bind(getItem(position))
    }


    override fun getItemCount(): Int {
        return listData.size
    }

    open    fun getItem(position: Int): T {
        return listData[position]
    }

    override fun getItemViewType(position: Int): Int {
        return getItemViewType(getItem(position))
    }

    /** Override this and return the integer type of the item */
    open fun getItemViewType(listItem: T): Int {
        return 0;
    }

    /** updates the list with new listData */
    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<T>) {
        this.listData = listData;
        notifyDataSetChanged()
    }
}