package com.githubsearchkotlin.presentation.di.login.module

import com.githubsearchkotlin.presentation.ui.activities.login.LoginActivity
import com.githubsearchkotlin.presentation.ui.routing.LoginRouter
import dagger.Binds
import dagger.Module

@Module
abstract class LoginModule {

    @Binds
    abstract fun bindRouter(activity: LoginActivity): LoginRouter

}