package com.githubsearchkotlin.presentation.ui.activities.main

import android.content.Context
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.githubsearchkotlin.base.repository.GithubNetworkSpecification
import com.githubsearchkotlin.base.repository.RepositoryCallBack
import com.githubsearchkotlin.base.viper.BasePresenter
import com.githubsearchkotlin.data.local.DatabaseHelper
import com.githubsearchkotlin.data.model.RepositoryItem
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.domain.GithubRepository
import com.githubsearchkotlin.presentation.ui.adapters.ContentRecycleOnClick
import com.githubsearchkotlin.presentation.ui.adapters.ContentRecyclerAdapter
import com.githubsearchkotlin.presentation.ui.holders.ViewHolderManager
import com.githubsearchkotlin.presentation.ui.routing.MainRouter
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor(router: MainRouter,val githubRepository: GithubRepository,
                                        val specification: GithubNetworkSpecification, val databaseHelper: DatabaseHelper) : BasePresenter<MainView, MainRouter>(), RepositoryCallBack<SearchRepoResponse>, ContentRecycleOnClick {

    private var contentRecyclerAdapter: ContentRecyclerAdapter<RepositoryItem>

    init {
        this@MainPresenter.router  = router
        githubRepository.callback = this
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
                    githubRepository.query(specification)
                }, { throwable -> Log.v("Error search : ", throwable.toString()) })
    }

    fun loadMore(page: Int) {
        mvpView?.showProgressBar()
        specification.loadMore = true
        specification.page = page
        githubRepository.query(specification)
    }

    override fun onSuccess(model: SearchRepoResponse, onMore: Boolean) {
        if (onMore) {
            contentRecyclerAdapter.uploadItems(model.items)
            mvpView?.hideProgressBar()
        } else {
            if (model.items.isEmpty()) {
                mvpView?.hideProgressBar()
                contentRecyclerAdapter.clearData()
                return
            }
            contentRecyclerAdapter.uploadItems(model.items)
            mvpView?.hideProgressBar()
        }
    }

    override fun onFailure(throwable: Throwable) {

    }

    override fun onClickItem(position: Int, id: Int, v: View) {
        val item: RepositoryItem = contentRecyclerAdapter.getItem(position)
        if (!item.isViewed) {
            databaseHelper.saveRepositoryItemPOJO(contentRecyclerAdapter.items[position])
            router?.startBrowserActivity(item.url)
            item.isViewed = true
            contentRecyclerAdapter.updateItem(item, position)
        } else {
            databaseHelper.getRecentSearchUrl(item.id)?.let {
                router?.startBrowserActivity(it)
            }
        }
    }

    fun stopSearch() {
        githubRepository.stopSearch(specification)
        mvpView?.hideProgressBar()
    }

    fun clearSearch() {
        specification.loadMore = false
        specification.page = 1
        specification.lastPage = 0
        contentRecyclerAdapter.clearData()
    }
}