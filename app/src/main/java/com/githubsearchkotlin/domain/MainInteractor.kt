//package com.githubsearchkotlin.domain
//
//import com.githubsearchkotlin.base.viper.Interactor
//import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
//import com.githubsearchkotlin.data.model.SearchRepoResponse
//import com.githubsearchkotlin.data.network.RepoSearchApiService
//import io.reactivex.Observable
//import io.reactivex.schedulers.Schedulers
//import javax.inject.Inject
//
//class MainInteractor @Inject constructor(var repoSearchApiService: RepoSearchApiService, var sharedPreferences: PreferencesHelper) :
//        Interactor<SearchRepoResponse>() {
//
//    lateinit var q: String
//    private var page = 1
//
//    override fun getApiObservable() : Observable<SearchRepoResponse> {
//      return   Observable.merge(
//              repoSearchApiService.searchRepo(sharedPreferences.loadUSerCredential(), q,
//                        OPTIONS.SORT_TYPE.param as String, OPTIONS.ORDER.param,
//                        OPTIONS.PER_PAGE.param as Int, page).observeOn(Schedulers.newThread()),
//
//              repoSearchApiService.searchRepo(sharedPreferences.loadUSerCredential(), q,
//                        OPTIONS.SORT_TYPE.param as String, OPTIONS.ORDER.param,
//                        OPTIONS.PER_PAGE.param as Int, page).observeOn(Schedulers.newThread())
//        )
//    }
//
//    enum class OPTIONS(val param: Any) {
//        SORT_TYPE("stars"),
//        ORDER("desc"),
//        PER_PAGE(15)
//    }
//
//}