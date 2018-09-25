package com.githubsearchkotlin.presentation.ui.routing

import com.githubsearchkotlin.base.viper.BaseRouter

interface RecentRouter : BaseRouter {
    fun startBrowserActivity(url: String)
}