package com.githubsearchkotlin.presentation.ui.activities.main

import android.content.Context
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.githubsearchkotlin.domain.specification.GithubItemsSpecification
import com.githubsearchkotlin.base.repository.RepositoryCallBack
import com.githubsearchkotlin.base.viper.BasePresenter
import com.githubsearchkotlin.data.local.DatabaseHelper
import com.githubsearchkotlin.data.model.RepositoryItem
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.domain.GithubNetworkRepositoryItemImpl
import com.githubsearchkotlin.presentation.ui.adapters.ContentRecycleOnClick
import com.githubsearchkotlin.presentation.ui.adapters.ContentRecyclerAdapter
import com.githubsearchkotlin.presentation.ui.holders.ViewHolderManager
import com.githubsearchkotlin.presentation.ui.routing.MainRouter
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter  @Inject constructor(router: MainRouter, val githubNetworkRepositoryItemImpl: GithubNetworkRepositoryItemImpl,
                                        val specification: GithubItemsSpecification, val databaseHelper: DatabaseHelper) : BasePresenter<MainView, MainRouter>(), ContentRecycleOnClick {

    private var contentRecyclerAdapter: ContentRecyclerAdapter<RepositoryItem>

    init {
        this@MainPresenter.router = router
        contentRecyclerAdapter = ContentRecyclerAdapter(router as Context, ViewHolderManager.SEARCH_REPO)
    }

    override fun attachView(mvpView: MainView) {
        super.attachView(mvpView)
        contentRecyclerAdapter.contentRecycleOnClick = this
        mvpView.initRecyclerData(contentRecyclerAdapter)
    }

    fun subscribeSearchView(searchView: SearchView) {
        RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter { charSequence ->
                    val empty = TextUtils.isEmpty(charSequence) || charSequence.toString().isEmpty()
                    !empty
                }
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe({ charSequence ->
                    mvpView?.showProgressBar()
                    clearSearch()
                    specification.q = charSequence.toString()
                    contentRecyclerAdapter.clearData()
                    onSuccess(githubNetworkRepositoryItemImpl.query(specification), false)
                }, { throwable -> Log.v("Error search : ", throwable.toString()) })
    }

    fun loadMore(page: Int) {
        mvpView?.showProgressBar()
        specification.loadMore = true
        specification.page = page
        githubNetworkRepositoryItemImpl.query(specification)
    }

    fun onSuccess(items: List<RepositoryItem>, onMore: Boolean) {
        if (onMore) {
            contentRecyclerAdapter.uploadItems(items)
            mvpView?.hideProgressBar()
        } else {
            if (items.isEmpty()) {
                mvpView?.hideProgressBar()
                contentRecyclerAdapter.clearData()
                mvpView?.showEmptyView()
                return
            }
            contentRecyclerAdapter.uploadItems(items)
            mvpView?.hideProgressBar()
            mvpView?.hideEmptyView()
        }
    }

     fun onFailure(throwable: Throwable) {

    }

    override fun onClickItem(position: Int, id: Int, v: View) {
        val item: RepositoryItem = contentRecyclerAdapter.getItem(position)
        if (!item.isViewed) {
            item.isViewed = true
            databaseHelper.saveRepositoryItemPOJO(contentRecyclerAdapter.items[position], position)
            router?.startBrowserActivity(item.url)
            contentRecyclerAdapter.updateItem(item, position)
        } else {
            databaseHelper.getRecentSearchUrl(item.id)?.let {
                router?.startBrowserActivity(it)
            }
        }
    }

    fun stopSearch() {
//        githubNetworkRepositoryItemImpl.stopSearch(specification)
        mvpView?.hideProgressBar()
    }

    fun clearSearch() {
        specification.loadMore = false
        specification.page = 1
        specification.lastPage = 0
        contentRecyclerAdapter.clearData()
        mvpView?.showEmptyView()
    }
}