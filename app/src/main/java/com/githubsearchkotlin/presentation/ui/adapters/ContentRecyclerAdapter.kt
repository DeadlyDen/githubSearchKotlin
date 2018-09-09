package com.githubsearchkotlin.presentation.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.githubsearchkotlin.presentation.ui.holders.ViewHolderManager

class ContentRecyclerAdapter<T>(val context: Context, holderType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<T> = ArrayList()

    lateinit var contentRecycleOnClick: ContentRecycleOnClick
    lateinit var contentRecycleOnLongClick: ContentRecycleOnLongClick

    private var viewHolderManager: ViewHolderManager? = null

    init {
        viewHolderManager = ViewHolderManager(holderType, context, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return viewHolderManager?.createHolder(parent)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        viewHolderManager?.initHolder(holder, items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(updatedItems: List<T>) {
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }

    fun uploadItems(updatedItems: List<T>) {
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }

    fun updateItem(item: T, position: Int) {
        items.set(position, item)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    fun clearData() {
        if (items.isNotEmpty()) {
            items.clear()
            notifyDataSetChanged()
        }
    }

}