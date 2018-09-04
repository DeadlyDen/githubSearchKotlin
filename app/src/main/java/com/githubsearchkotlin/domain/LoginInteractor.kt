package com.githubsearchkotlin.domain

import com.githubsearchkotlin.base.viper.Interactor
import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
import com.githubsearchkotlin.data.model.UserResponse
import com.githubsearchkotlin.data.network.ApiService
import io.reactivex.Observable
import javax.inject.Inject

class LoginInteractor @Inject constructor(val apiService: ApiService,
                                          val preferencesHelper: PreferencesHelper
                                          ) : Interactor<UserResponse>()  {
    var credential : String? = null

    override fun getApiObservable(): Observable<UserResponse> {
       return apiService.doLogin(credential!!)
    }

    fun doLlogin() {
        credential?.let {
            preferencesHelper.saveUserCredential(it)
            fetch()
        }
    }

}