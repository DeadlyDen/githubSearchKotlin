package com.githubsearchkotlin.presentation.di.login.component

import com.githubsearchkotlin.presentation.di.login.module.LoginModule
import com.githubsearchkotlin.presentation.ui.activities.login.LoginActivity
import com.hily.android.presentation.di.scopes.ActivityScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [LoginModule::class])
interface LoginSubComponent : AndroidInjector<LoginActivity> {

    @ActivityScope
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<LoginActivity>()
}