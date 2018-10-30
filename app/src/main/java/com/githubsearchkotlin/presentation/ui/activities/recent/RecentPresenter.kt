package com.githubsearchkotlin.presentation.ui.activities.recent

import android.content.Context
import android.view.View
import com.githubsearchkotlin.domain.specification.GithubORMSpecification
import com.githubsearchkotlin.base.repository.RepositoryCallBack
import com.githubsearchkotlin.base.viper.BasePresenter
import com.githubsearchkotlin.data.local.DatabaseHelper
import com.githubsearchkotlin.data.model.RepositoryItem
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.domain.GithubLocalRepositoryItemImpl
import com.githubsearchkotlin.domain.GithubNetworkRepositoryItemImpl
import com.githubsearchkotlin.presentation.ui.adapters.ContentRecycleOnClick
import com.githubsearchkotlin.presentation.ui.adapters.ContentRecycleOnMove
import com.githubsearchkotlin.presentation.ui.adapters.ContentRecyclerAdapter
import com.githubsearchkotlin.presentation.ui.holders.ViewHolderManager
import com.githubsearchkotlin.presentation.ui.routing.RecentRouter
import com.githubsearchkotlin.presentation.ui.utils.RxBus
import javax.inject.Inject

class RecentPresenter @Inject constructor(router: RecentRouter, val githubLocalRepositoryItemImpl: GithubLocalRepositoryItemImpl,
                                          val specification: GithubORMSpecification, val databaseHelper: DatabaseHelper) : BasePresenter<RecentView, RecentRouter>(),
        RepositoryCallBack<SearchRepoResponse>, ContentRecycleOnClick, ContentRecycleOnMove{

    private var contentRecyclerAdapter: ContentRecyclerAdapter<RepositoryItem>

    init {
        this@RecentPresenter.router = router
        githubLocalRepositoryItemImpl.callback = this
        contentRecyclerAdapter = ContentRecyclerAdapter(router as Context, ViewHolderManager.SEARCH_REPO)
    }


    override fun attachView(mvpView: RecentView) {
        super.attachView(mvpView)
        contentRecyclerAdapter.contentRecycleOnClick = this
        contentRecyclerAdapter.contentRecycleOnMove = this
        mvpView.initRecyclerData(contentRecyclerAdapter)
        githubLocalRepositoryItemImpl.query(specification)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun onSuccess(model: SearchRepoResponse, onMore: Boolean) {
        if (model.items.isEmpty()) {
            mvpView?.showEmptyView()
        }
        contentRecyclerAdapter.uploadItems(model.items)

    }

    override fun onFailure(throwable: Throwable) {

    }

    override fun onClickItem(position: Int, id: Int, v: View) {
        val item: RepositoryItem = contentRecyclerAdapter.getItem(position)
        router?.startBrowserActivity(item.url)
    }

    override fun onItemMove() {
        contentRecyclerAdapter.items.forEachIndexed { index, repositoryItem -> databaseHelper.updatePositionRecentSearchItem(repositoryItem.id, index) }
    }

    override fun onItemDismiss(position: Int) {
        val item: RepositoryItem = contentRecyclerAdapter.getItem(position)
        databaseHelper.deleteRepositoryItem(item.id)
        RxBus.publish(RxBus.UPDATE_REPOSITORY_ITEM, item.id)
    }
}