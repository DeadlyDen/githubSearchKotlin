package com.githubsearchkotlin.presentation.di.main.component

import com.githubsearchkotlin.presentation.di.main.module.MainModule
import com.githubsearchkotlin.presentation.ui.activities.main.MainActivity
import com.hily.android.presentation.di.scopes.ActivityScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [MainModule::class])
interface MainSubComponent : AndroidInjector<MainActivity> {

    @ActivityScope
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()

}