package com.githubsearchkotlin.presentation.di.recent.conponent

import com.githubsearchkotlin.presentation.di.recent.module.RecentModule
import com.githubsearchkotlin.presentation.ui.activities.recent.RecentActivity
import com.hily.android.presentation.di.scopes.ActivityScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [RecentModule::class])
interface RecentSubComponent : AndroidInjector<RecentActivity> {

    @ActivityScope
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<RecentActivity>()

}