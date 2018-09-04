package com.githubsearchkotlin.presentation.ui.activities.main

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.githubsearchkotlin.R
import com.githubsearchkotlin.base.viper.HideShowContentView
import com.githubsearchkotlin.base.viper.View
import com.githubsearchkotlin.presentation.ui.activities.BaseActivity
import com.githubsearchkotlin.presentation.ui.routing.MainRouter
import javax.inject.Inject

interface MainView : View, HideShowContentView {

    fun initRecyclerData(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>)

}

class MainActivity(@Inject var mainPresenter: MainPresenter) : BaseActivity(), MainView, MainRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter.attachView(this)
    }

    override fun initRecyclerData(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {

    }


}
