package com.githubsearchkotlin.presentation.ui.activities.main

import com.githubsearchkotlin.base.viper.BasePresenter
import com.githubsearchkotlin.base.viper.InteractorCallback
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.domain.MainInteractor
import com.githubsearchkotlin.presentation.ui.routing.MainRouter
import javax.inject.Inject

class MainPresenter @Inject constructor(val mainInteractor: MainInteractor) : BasePresenter<MainView, MainRouter>(), InteractorCallback<SearchRepoResponse> {

    init {
        mainInteractor.callback = this
    }

    override fun onSuccess(model: SearchRepoResponse?, onMore: Boolean) {

    }

    override fun onFailure(throwable: Throwable?) {

    }
}