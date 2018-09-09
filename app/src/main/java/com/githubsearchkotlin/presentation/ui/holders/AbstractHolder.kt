package com.githubsearchkotlin.presentation.ui.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.ButterKnife

abstract class AbstractHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var item: T? = null

    init {
        ButterKnife.bind(this, itemView)
    }

    abstract fun initData(context: Context)
}