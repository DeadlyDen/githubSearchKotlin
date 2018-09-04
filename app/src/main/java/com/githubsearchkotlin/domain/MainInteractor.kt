package com.githubsearchkotlin.domain

import com.githubsearchkotlin.base.viper.Interactor
import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
import com.githubsearchkotlin.data.model.SearchRepoZipResponse
import com.githubsearchkotlin.data.network.ApiHelper
import io.reactivex.Observable
import javax.inject.Inject

class MainInteractor @Inject constructor(var apiHelper: ApiHelper, var sharedPreferences: PreferencesHelper) :
        Interactor<SearchRepoZipResponse>() {

    lateinit var q: String
    private var page = 1

    override fun getApiObservable(): Observable<SearchRepoZipResponse> {
        return Observable.zip(
                apiHelper.searchRepo(sharedPreferences.loadUSerCredential(), q,
                        OPTIONS.SORT_TYPE.param as String, OPTIONS.ORDER.param,
                        OPTIONS.PER_PAGE.param as Int, page),
                apiHelper.searchRepo(sharedPreferences.loadUSerCredential(), q,
                        OPTIONS.SORT_TYPE.param as String, OPTIONS.ORDER.param,
                        OPTIONS.PER_PAGE.param as Int, page)
        )
    }

    enum class OPTIONS(val param: Any) {
        SORT_TYPE("stars"),
        ORDER("desc"),
        PER_PAGE(15)
    }

}