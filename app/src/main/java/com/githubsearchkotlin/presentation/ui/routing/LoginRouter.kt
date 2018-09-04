package com.githubsearchkotlin.presentation.ui.routing

import com.githubsearchkotlin.base.viper.BaseRouter
import com.githubsearchkotlin.data.model.UserResponse

interface LoginRouter : BaseRouter {

    fun starSearchRepoActivity(userResponse: UserResponse)
}