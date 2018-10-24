package com.githubsearchkotlin.domain.specification

import com.githubsearchkotlin.base.repository.BaseNetworkSpecification
import com.githubsearchkotlin.data.local.DatabaseHelper
import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
import com.githubsearchkotlin.data.model.RepositoryItem
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.data.network.RepoSearchApiService
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubItemsSpecification @Inject constructor(var repoSearchApiService: RepoSearchApiService,
                                                   var databaseHelper: DatabaseHelper,
                                                   var sharedPreferences: PreferencesHelper) : BaseNetworkSpecification<SearchRepoResponse> {

    var q: String = ""
    var page = 0
    var lastPage = 0
    var loadMore = false

    var recentRepository: ArrayList<Int> = ArrayList()

    init {
        recentRepository = databaseHelper.getResentSearchRepoId()
    }

    override fun query(): Observable<SearchRepoResponse> {
                return Observable.zip(
                repoSearchApiService.searchRepo(sharedPreferences.loadUSerCredential(), q,
                        OPTIONS.SORT_TYPE.param as String, OPTIONS.ORDER.param,
                        OPTIONS.PER_PAGE.param as Int, page + lastPage).observeOn(Schedulers.newThread()),
                repoSearchApiService.searchRepo(sharedPreferences.loadUSerCredential(), q,
                        OPTIONS.SORT_TYPE.param as String, OPTIONS.ORDER.param,
                        OPTIONS.PER_PAGE.param as Int, page + page).observeOn(Schedulers.newThread()),
                BiFunction<SearchRepoResponse, SearchRepoResponse, SearchRepoResponse> { t1, t2 ->
                    t1.items.addAll(t2.items)
                    t1.items.forEach { repositoryItem: RepositoryItem ->
                        if (recentRepository.contains(repositoryItem.id)) {
                            repositoryItem.isViewed = true
                        } }
                    t1
                }
        )
    }

    enum class OPTIONS(val param: Any) {
        SORT_TYPE("stars"),
        ORDER("desc"),
        PER_PAGE(15)
    }

}