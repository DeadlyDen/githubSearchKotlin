package com.githubsearchkotlin.presentation.di.main.module

import com.githubsearchkotlin.presentation.ui.activities.main.MainActivity
import com.githubsearchkotlin.presentation.ui.routing.MainRouter
import dagger.Binds
import dagger.Module

@Module
abstract class MainModule {

    @Binds
    abstract fun bindRouter(activity: MainActivity) : MainRouter


}