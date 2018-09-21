package com.githubsearchkotlin.domain

import com.githubsearchkotlin.base.repository.BaseNetworkSpecification
import com.githubsearchkotlin.base.repository.BaseRepository
import com.githubsearchkotlin.base.repository.RepositoryCallBack
import com.githubsearchkotlin.base.repository.Specification
import com.githubsearchkotlin.data.local.DatabaseHelper
import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.data.network.RepoSearchApiService
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepository @Inject constructor(var repoSearchApiService: RepoSearchApiService,
                                           var sharedPreferences: PreferencesHelper, var databaseHelper: DatabaseHelper) : BaseRepository {

    lateinit var callback: RepositoryCallBack<SearchRepoResponse>

    var q: String = ""
    var page = 1
    var lastPage = 0
    var loadMore = false

    private fun createRepositoryObservable (): Observable<SearchRepoResponse> {
        return Observable.zip(
                repoSearchApiService.searchRepo(sharedPreferences.loadUSerCredential(), q,
                        OPTIONS.SORT_TYPE.param as String, OPTIONS.ORDER.param,
                        OPTIONS.PER_PAGE.param as Int, page + lastPage).observeOn(Schedulers.newThread()),
                repoSearchApiService.searchRepo(sharedPreferences.loadUSerCredential(), q,
                        OPTIONS.SORT_TYPE.param as String, OPTIONS.ORDER.param,
                        OPTIONS.PER_PAGE.param as Int, page + page).observeOn(Schedulers.newThread()),
                BiFunction<SearchRepoResponse, SearchRepoResponse, SearchRepoResponse> { t1, t2 ->
                    t1.items.addAll(t2.items)
                     t1
                }
        )
    }

    override fun query(specification: Specification) {
        when (specification) {
            is BaseNetworkSpecification -> specification.load(createRepositoryObservable(),
                    if (loadMore) specification.getMoreObserver(callback) else specification.getObserver(callback))
        }
    }

    enum class OPTIONS(val param: Any) {
        SORT_TYPE("stars"),
        ORDER("desc"),
        PER_PAGE(15)
    }

}