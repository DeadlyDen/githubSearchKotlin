package com.githubsearchkotlin.presentation.di.login.module

import android.app.Activity
import com.githubsearchkotlin.presentation.di.login.component.LoginSubComponent
import com.githubsearchkotlin.presentation.ui.activities.login.LoginActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [LoginSubComponent::class])
abstract class LoginBindModule {

    @Binds
    @IntoMap
    @ActivityKey(LoginActivity::class)
    abstract fun bindLoginActivityInjectorFactory(builder: LoginSubComponent.Builder): AndroidInjector.Factory<out Activity>

}