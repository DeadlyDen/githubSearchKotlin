package com.githubsearchkotlin.presentation.di.app

import com.githubsearchkotlin.BuildConfig
import com.githubsearchkotlin.data.network.ApiHelper
import com.githubsearchkotlin.data.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    val TIMEOUT = 30
    val API_BASE_URL = "https://api.github.com/"

    private val httpClient = OkHttpClient.Builder()
    private val builder = getBuilder()

    @Provides
    @Singleton
    fun provideApiClient(): ApiService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(logging)
        }
        httpClient.connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)

        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiService: ApiService) : ApiHelper {
        return ApiHelper(apiService)
    }


    private fun getBuilder(): Retrofit.Builder {
        val builder = Retrofit.Builder()
        if (BuildConfig.DEBUG) {
            builder.baseUrl(API_BASE_URL)
        } else {
            builder.baseUrl(API_BASE_URL)
        }
        builder.addConverterFactory(GsonConverterFactory.create())
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return builder
    }

}