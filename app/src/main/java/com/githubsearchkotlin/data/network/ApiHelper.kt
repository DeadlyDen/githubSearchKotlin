package com.githubsearchkotlin.data.network

import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.data.model.UserResponse
import dagger.Module
import io.reactivex.Observable
import javax.inject.Inject

@Module
class ApiHelper @Inject constructor (var apiService: ApiService) : ApiService {

    override fun doLogin(authorization: String): Observable<UserResponse> {
        return apiService.doLogin(authorization)
    }

    override fun searchRepo(authorization: String, q: String, sort: String, order: String, per: Int, page: Int): Observable<SearchRepoResponse> {
        return apiService.searchRepo(authorization, q, sort, order, per, page)
    }


}