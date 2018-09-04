package com.githubsearchkotlin

import android.app.Activity
import android.app.Application
import com.githubsearchkotlin.presentation.di.app.AppComponent
import com.githubsearchkotlin.presentation.di.app.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject



class App : Application(), HasActivityInjector {

    lateinit var mAppComponent: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()

        mAppComponent = buildComponent()
        mAppComponent.inject(this)
    }

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent
                .builder()
                .application(this)
                .build()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

}