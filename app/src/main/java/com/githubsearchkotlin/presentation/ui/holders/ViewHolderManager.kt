package com.githubsearchkotlin.presentation.ui.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubsearchkotlin.R
import com.githubsearchkotlin.data.model.RepositoryItem
import com.githubsearchkotlin.presentation.ui.adapters.ContentRecyclerAdapter

class ViewHolderManager(val holderType: Int, val context: Context, val contentRecyclerAdapter: ContentRecyclerAdapter<*>) {

    var selectedPos: Int = 0

    fun createHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        when (holderType) {
            SEARCH_REPO -> return SearchRepositoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_search_repo, parent, false))
            else -> return null
        }

    }

    fun <T> initHolder(holder: RecyclerView.ViewHolder, item: T, position: Int) {
        when (holderType) {
            SEARCH_REPO -> {
                val searchRepoViewHolder = holder as SearchRepositoryViewHolder
                val itemRepo = item as RepositoryItem
                searchRepoViewHolder.item = itemRepo
                searchRepoViewHolder.initData(context)
                searchRepoViewHolder.itemView.setOnClickListener { view -> contentRecyclerAdapter.contentRecycleOnClick?.onClickItem(position, view.id, view) }
                searchRepoViewHolder.itemView.setOnLongClickListener { view ->
                    if (contentRecyclerAdapter.contentRecycleOnLongClick != null) contentRecyclerAdapter.contentRecycleOnLongClick!!.OnLongClickItem(position, view.id, view) else false }
                if (itemRepo.isViewed) {
                    searchRepoViewHolder.viewedStatus.visibility = View.VISIBLE
                    searchRepoViewHolder.viewedStatus.text = context.getString(R.string.viewed)
                } else {
                    searchRepoViewHolder.viewedStatus.visibility = View.GONE
                }
            }
            else -> {
            }
        }
    }

    companion object {
        val SEARCH_REPO = 0
    }

}