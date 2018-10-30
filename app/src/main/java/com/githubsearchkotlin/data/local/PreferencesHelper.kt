package com.githubsearchkotlin.data.localPreferencesHelper

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val USER_CREDENTIAL = "user_credential"
    private val USER_VIEWED_REPO = "user_repositories"
    private val FIRST_SESSION = "user_first_session"


    fun saveUserCredential(credential: String) {
        sharedPreferences.edit().putString(USER_CREDENTIAL, credential).apply()
    }

    fun loadUSerCredential(): String {
        return sharedPreferences.getString(USER_CREDENTIAL, null)
    }

    fun clearUserCredential() {
        sharedPreferences.edit().remove(USER_CREDENTIAL).apply()
    }

    fun updateFirstSessionState(state: Boolean) {
        sharedPreferences.edit().putBoolean(FIRST_SESSION, state).apply()
    }

    fun getFirstSessionState(): Boolean {
        return sharedPreferences.getBoolean(FIRST_SESSION, true)
    }
}