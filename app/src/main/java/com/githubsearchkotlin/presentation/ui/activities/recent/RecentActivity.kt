package com.githubsearchkotlin.presentation.ui.activities.recent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import butterknife.BindView
import butterknife.ButterKnife
import com.githubsearchkotlin.R
import com.githubsearchkotlin.base.viper.HideShowContentView
import com.githubsearchkotlin.base.viper.View
import com.githubsearchkotlin.presentation.ui.activities.BaseActivity
import com.githubsearchkotlin.presentation.ui.adapters.ContentRecyclerAdapter
import com.githubsearchkotlin.presentation.ui.adapters.ItemTouchHelperCallback
import com.githubsearchkotlin.presentation.ui.routing.RecentRouter
import javax.inject.Inject

interface RecentView : View, HideShowContentView {
    fun initRecyclerData(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>)
}

class RecentActivity : BaseActivity(), RecentView, RecentRouter {

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var recentPresenter: RecentPresenter

    private var linearLayoutManager = LinearLayoutManager(this)

    lateinit var touchCallBack: ItemTouchHelperCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent)
        ButterKnife.bind(this)
        recentPresenter.attachView(this)
    }

    override fun initRecyclerData(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false
        touchCallBack = ItemTouchHelperCallback(adapter as ContentRecyclerAdapter<*>)
        val itemTouchHelper = ItemTouchHelper(touchCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    override fun startBrowserActivity(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onBackPressed() {
        finish()
    }
}
