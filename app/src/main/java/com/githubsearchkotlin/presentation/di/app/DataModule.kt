package com.githubsearchkotlin.presentation.di.app

import android.content.Context
import android.content.SharedPreferences
import com.githubsearchkotlin.data.local.PreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {


    @Singleton
    @Provides
    fun providePreferencesHelper(context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }
}