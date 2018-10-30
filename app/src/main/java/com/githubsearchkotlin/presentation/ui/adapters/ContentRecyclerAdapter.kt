package com.githubsearchkotlin.presentation.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.githubsearchkotlin.presentation.ui.holders.ViewHolderManager
import com.githubsearchkotlin.presentation.ui.utils.ItemTouchHelperAdapter
import java.util.*


class ContentRecyclerAdapter<T>(val context: Context, holderType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    var items: ArrayList<T> = ArrayList()

    var contentRecycleOnClick: ContentRecycleOnClick? = null
    var contentRecycleOnLongClick: ContentRecycleOnLongClick? = null
    var contentRecycleOnMove: ContentRecycleOnMove? = null

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
        if (items.isNotEmpty()) {
            items.clear()
        }
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }

    fun uploadItems(updatedItems: List<T>) {
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }

    fun updateItem(item: T, position: Int) {
        items.set(position, item)
        notifyItemChanged(position)
    }

    fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    fun clearData() {
        if (items.isNotEmpty()) {
            items.clear()
            notifyDataSetChanged()
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        contentRecycleOnMove?.onItemMove()
    }

    override fun onItemDismiss(position: Int) {
        contentRecycleOnMove?.onItemDismiss(position)
        deleteItem(position)
    }
}