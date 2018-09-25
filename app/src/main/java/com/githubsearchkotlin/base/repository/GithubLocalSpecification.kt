package com.githubsearchkotlin.base.repository

import com.githubsearchkotlin.data.local.DatabaseHelper
import com.githubsearchkotlin.data.model.SearchRepoResponse
import io.reactivex.Observable
import javax.inject.Inject

class GithubLocalSpecification @Inject constructor(var databaseHelper: DatabaseHelper) : BasekLocalSpecification<SearchRepoResponse> {


    override fun getQuery(): Observable<SearchRepoResponse> {
      return databaseHelper.getRepositoryItems()
    }


}