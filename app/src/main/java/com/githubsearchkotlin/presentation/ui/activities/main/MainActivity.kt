package com.githubsearchkotlin.presentation.ui.activities.main

import android.os.Bundle
import com.githubsearchkotlin.R
import com.githubsearchkotlin.base.viper.HideShowContentView
import com.githubsearchkotlin.base.viper.View
import com.githubsearchkotlin.presentation.ui.activities.BaseActivity
import com.githubsearchkotlin.presentation.ui.routing.MainRouter
import javax.inject.Inject

interface MainView : View, HideShowContentView {

}

class MainActivity(@Inject var mainPresenter: MainPresenter) : BaseActivity(), MainView, MainRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter.attachView(this)
    }
}
