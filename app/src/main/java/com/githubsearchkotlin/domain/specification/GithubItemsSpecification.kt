package com.githubsearchkotlin.domain.specification

import com.githubsearchkotlin.base.repository.BaseNetworkSpecification
import com.githubsearchkotlin.data.local.DatabaseHelper
import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.data.network.RepoSearchApiService
import com.githubsearchkotlin.domain.UseCase
import javax.inject.Inject

class GithubItemsSpecification @Inject constructor(var repoSearchApiService: RepoSearchApiService,
                                                   var databaseHelper: DatabaseHelper,
                                                   var sharedPreferences: PreferencesHelper) : BaseNetworkSpecification<SearchRepoResponse>, UseCase<SearchRepoResponse>() {

    var q: String = ""
    var page = 0
    var lastPage = 0
    var loadMore = false

    var recentRepository: ArrayList<Int> = ArrayList()

    init {
        recentRepository = databaseHelper.getResentSearchRepoId()
    }

    override fun query(): SearchRepoResponse {

        return SearchRepoResponse()

    }

    override suspend fun executeOnBackground(): SearchRepoResponse {
        val respone1 = background {
            repoSearchApiService.searchRepo(sharedPreferences.loadUSerCredential(), q,
                    OPTIONS.SORT_TYPE.param as String, OPTIONS.ORDER.param,
                    OPTIONS.PER_PAGE.param as Int, page + lastPage)
        }.await()
        val response2 = background {
            repoSearchApiService.searchRepo(sharedPreferences.loadUSerCredential(), q,
                    OPTIONS.SORT_TYPE.param as String, OPTIONS.ORDER.param,
                    OPTIONS.PER_PAGE.param as Int, page + page)
        }.await()
        respone1.await().items.addAll(response2.await().items)
        return respone1.getCompleted()
    }

    enum class OPTIONS(val param: Any) {
        SORT_TYPE("stars"),
        ORDER("desc"),
        PER_PAGE(15)
    }

}