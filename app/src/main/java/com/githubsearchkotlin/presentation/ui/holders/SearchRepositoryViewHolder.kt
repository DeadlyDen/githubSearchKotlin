package com.githubsearchkotlin.presentation.ui.holders

import android.content.Context
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.githubsearchkotlin.R
import com.githubsearchkotlin.data.model.RepositoryItem

class SearchRepositoryViewHolder(itemView: View) : AbstractHolder<RepositoryItem>(itemView) {

    @BindView(R.id.repo_name)
    lateinit var repoName: TextView
    @BindView(R.id.viewed)
    lateinit var viewedStatus: TextView

    override fun initData(context: Context) {
        val item = item
        val maxSymbols = 30

        item?.let {
            if (it.name.length > maxSymbols) {
                repoName.text = item.name.substring(0, maxSymbols).plus(context.getString(R.string.three_dots))
            } else {
                repoName.text = item.name
            }
        }
    }
}