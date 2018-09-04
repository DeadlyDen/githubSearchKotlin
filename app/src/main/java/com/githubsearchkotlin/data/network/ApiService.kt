package com.githubsearchkotlin.data.network

import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.data.model.UserResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by Den on 31.05.2017.
 */

interface ApiService {

    @GET("user")
     fun doLogin(@Header("Authorization") authorization: String) : Observable<UserResponse>
    @GET("search/repositories")
    fun searchRepo(@Header("Authorization")  authorization : String,
                   @Query("q")  q : String, @Query("sort")  sort : String,
                   @Query("order")  order : String, @Query("per_page")  per : Int,
                   @Query("page") page : Int) : Observable<SearchRepoResponse>

}


