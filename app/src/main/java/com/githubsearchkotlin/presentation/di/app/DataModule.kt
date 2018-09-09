package com.githubsearchkotlin.presentation.di.app

import android.content.Context
import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {


    @Singleton
    @Provides
    fun providePreferencesHelper(context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }
}