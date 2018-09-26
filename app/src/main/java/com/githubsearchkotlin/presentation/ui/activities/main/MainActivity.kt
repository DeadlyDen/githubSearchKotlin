package com.githubsearchkotlin.presentation.ui.activities.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.github.ybq.android.spinkit.SpinKitView
import com.githubsearchkotlin.R
import com.githubsearchkotlin.base.viper.HideShowContentView
import com.githubsearchkotlin.base.viper.View
import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
import com.githubsearchkotlin.presentation.ui.activities.BaseActivity
import com.githubsearchkotlin.presentation.ui.activities.login.LoginActivity
import com.githubsearchkotlin.presentation.ui.routing.MainRouter
import com.githubsearchkotlin.presentation.ui.utils.EndlessRecyclerViewScrollListener
import com.githubsearchkotlin.presentation.ui.utils.UiUtils
import javax.inject.Inject


interface MainView : View, HideShowContentView {

    fun initRecyclerData(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>)
    fun showProgressBar()
    fun hideProgressBar()
}

class MainActivity : BaseActivity(), MainView, MainRouter, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.drawer_layout)
    lateinit var drawerLayout: DrawerLayout
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.search_view)
    lateinit var searchView: SearchView
    @BindView(android.support.v7.appcompat.R.id.search_src_text)
    lateinit var txtSearch: EditText
    @BindView(R.id.nav_view)
    lateinit var navigationView: NavigationView
    @BindView(R.id.spin_kit_more)
    lateinit var progressBar: SpinKitView
    @Inject
    lateinit var mainPresenter: MainPresenter
    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    private var mBackPressed: Long = 0
    private var linearLayoutManager = LinearLayoutManager(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initToolBar()
        mainPresenter.attachView(this)
        mainPresenter.subscribeSearchView(searchView)
    }

    private fun initToolBar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)

        searchView.setIconifiedByDefault(false)
        txtSearch.hint = resources.getString(R.string.search_hint)
        txtSearch.setHintTextColor(getColor(R.color.colorAccent))
        txtSearch.setTextColor(getColor(R.color.colorAccent))
    }

    override fun initRecyclerData(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                mainPresenter.loadMore(page)
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_clear -> {
                searchView.setQuery("", false)
                mainPresenter.clearSearch()
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.nav_logout -> {
                preferencesHelper.clearUserCredential()
                UiUtils.closeKeyboard(this)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return true
            }
            else -> return false
        }
    }

    override fun startBrowserActivity(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    @OnClick(R.id.stop_search)
    fun stopSearch() {
        mainPresenter.stopSearch()
    }

    override fun showProgressBar() {
        progressBar.visibility = android.view.View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = android.view.View.GONE
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
