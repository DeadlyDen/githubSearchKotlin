package com.githubsearchkotlin.presentation.ui.routing

import android.content.Intent
import com.githubsearchkotlin.base.viper.BaseRouter

interface MainRouter : BaseRouter {

    fun startBrowserActivity(url: String)

}