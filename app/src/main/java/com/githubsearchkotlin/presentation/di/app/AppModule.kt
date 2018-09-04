package com.githubsearchkotlin.presentation.di.app

import android.content.Context
import com.githubsearchkotlin.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: App): Context {
        return app.applicationContext
    }

}