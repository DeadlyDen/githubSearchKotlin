package com.githubsearchkotlin.presentation.di.main.module

import android.app.Activity
import com.githubsearchkotlin.presentation.di.main.component.MainSubComponent
import com.githubsearchkotlin.presentation.ui.activities.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainSubComponent::class])
abstract class MainBindModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainSubComponent.Builder): AndroidInjector.Factory<out Activity>

}