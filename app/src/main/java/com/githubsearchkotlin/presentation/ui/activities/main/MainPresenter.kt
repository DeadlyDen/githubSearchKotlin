package com.githubsearchkotlin.presentation.ui.activities.main

import com.githubsearchkotlin.base.viper.BasePresenter
import com.githubsearchkotlin.base.viper.InteractorCallback
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.presentation.ui.routing.MainRouter

class MainPresenter : BasePresenter<MainView, MainRouter>(), InteractorCallback<SearchRepoResponse> {

    init {

    }

    override fun onSuccess(model: SearchRepoResponse?, onMore: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure(throwable: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}