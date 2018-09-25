package com.githubsearchkotlin.presentation.di.recent.module

import android.app.Activity
import com.githubsearchkotlin.presentation.di.recent.conponent.RecentSubComponent
import com.githubsearchkotlin.presentation.ui.activities.recent.RecentActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [RecentSubComponent::class])
abstract class RecentBindModule {

    @Binds
    @IntoMap
    @ActivityKey(RecentActivity::class)
    abstract fun bindRecentActivityInjectorFactory(builder: RecentSubComponent.Builder): AndroidInjector.Factory<out Activity>

}