package com.githubsearchkotlin.domain.specification

import com.githubsearchkotlin.base.repository.BaseORMSpecification
import com.githubsearchkotlin.data.local.DatabaseHelper
import com.githubsearchkotlin.data.model.SearchRepoResponse
import io.reactivex.Observable
import javax.inject.Inject

class GithubORMSpecification @Inject constructor(var databaseHelper: DatabaseHelper) : BaseORMSpecification<SearchRepoResponse> {

    override fun query(): SearchRepoResponse {
      return databaseHelper.getRepositoryItems()
    }


}