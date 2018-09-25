package com.githubsearchkotlin.presentation.di.recent.module

import com.githubsearchkotlin.presentation.ui.activities.recent.RecentActivity
import com.githubsearchkotlin.presentation.ui.routing.RecentRouter
import dagger.Binds
import dagger.Module

@Module
abstract class RecentModule {

    @Binds
    abstract fun bindRouter(activity: RecentActivity) : RecentRouter
}