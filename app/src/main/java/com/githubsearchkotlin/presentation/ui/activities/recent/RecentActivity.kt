package com.githubsearchkotlin.presentation.ui.activities.recent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.github.ybq.android.spinkit.SpinKitView
import com.githubsearchkotlin.R
import com.githubsearchkotlin.base.viper.HideShowContentView
import com.githubsearchkotlin.base.viper.View
import com.githubsearchkotlin.presentation.ui.activities.BaseActivity
import com.githubsearchkotlin.presentation.ui.routing.RecentRouter
import javax.inject.Inject

interface RecentView : View, HideShowContentView {
    fun initRecyclerData(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>)
    fun showProgressBar()
    fun hideProgressBar()
}

class RecentActivity : BaseActivity(), RecentView, RecentRouter {

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.spin_kit_more)
    lateinit var progressBar: SpinKitView

    @Inject
    lateinit var recentPresenter : RecentPresenter

    private var mBackPressed: Long = 0
    private var linearLayoutManager = LinearLayoutManager(this)

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
    }

    override fun showProgressBar() {
        progressBar.visibility = android.view.View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = android.view.View.GONE
    }

    override fun startBrowserActivity(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            showMessageSnack("Click again to close!")
        }
        mBackPressed = System.currentTimeMillis()
    }

    companion object {
        val TIME_INTERVAL = 2000

    }
}
