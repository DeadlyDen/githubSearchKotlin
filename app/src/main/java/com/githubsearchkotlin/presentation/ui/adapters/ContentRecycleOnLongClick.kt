package com.githubsearchkotlin.presentation.ui.adapters

import android.view.View

interface ContentRecycleOnLongClick {
    fun OnLongClickItem(position: Int, id: Int, v: View): Boolean
}
