package com.githubsearchkotlin.presentation.ui.activities.main

import android.content.Context
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.githubsearchkotlin.base.repository.BaseNetworkSpecification
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
                                        val specification: BaseNetworkSpecification, val databaseHelper: DatabaseHelper) : BasePresenter<MainView, MainRouter>(), RepositoryCallBack<SearchRepoResponse>, ContentRecycleOnClick {

    private var contentRecyclerAdapter: ContentRecyclerAdapter<RepositoryItem>

    init {
        githubRepository.callback = this
        contentRecyclerAdapter = ContentRecyclerAdapter(router as Context, ViewHolderManager.SEARCH_REPO)
        contentRecyclerAdapter.contentRecycleOnClick = this
    }

    override fun attachView(mvpView: MainView) {
        super.attachView(mvpView)
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
                    mvpView?.showLoading()
                    githubRepository.loadMore = false
                    githubRepository.page = 1
                    githubRepository.lastPage = 0
                    githubRepository.q = charSequence.toString()
                    contentRecyclerAdapter.clearData()
                    githubRepository.query(specification)
                }, { throwable -> Log.v("Error search : ", throwable.toString()) })
    }

    fun loadMore(page: Int) {
        mvpView?.showLoading()
        githubRepository.loadMore = true
        githubRepository.page = page
        githubRepository.query(specification)
    }

    override fun onSuccess(model: SearchRepoResponse, onMore: Boolean) {
        if (onMore) {
            contentRecyclerAdapter.uploadItems(model.items)
            mvpView?.hideLoading()
        } else {
            if (model.items.isEmpty()) {
                mvpView?.hideLoading()
                contentRecyclerAdapter.clearData()
                return
            }
            contentRecyclerAdapter.uploadItems(model.items)
            mvpView?.hideLoading()
        }
    }

    override fun onFailure(throwable: Throwable) {

    }

    override fun onClickItem(position: Int, id: Int, v: View) {
        databaseHelper.saveRepoitoryItemPOJO(contentRecyclerAdapter.items[position])
    }
}