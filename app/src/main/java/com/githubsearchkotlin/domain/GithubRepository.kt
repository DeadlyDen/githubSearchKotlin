package com.githubsearchkotlin.domain

import com.githubsearchkotlin.base.repository.*
import com.githubsearchkotlin.data.local.DatabaseHelper
import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
import com.githubsearchkotlin.data.model.RepositoryItem
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.data.network.RepoSearchApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepository : BaseRepository {

    lateinit var callback: RepositoryCallBack<SearchRepoResponse>

    private var disposables: CompositeDisposable = CompositeDisposable()


    override fun query(specification: Specification) {
        when (specification) {
            is GithubNetworkSpecification -> {
                disposables.add(specification.getQuery()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(object : DisposableObserver<SearchRepoResponse>() {
                            override fun onNext(t: SearchRepoResponse) {
                                callback.onSuccess(t, specification.loadMore)
                            }

                            override fun onError(e: Throwable) {
                                callback.onFailure(e)
                            }

                            override fun onComplete() {

                            }
                        }))
                specification.lastPage = specification.page
            }
        }
    }

    fun stopSearch(specification: Specification) {
        when (specification) {
            is GithubNetworkSpecification -> {
                if (disposables.size() != 0) {
                    disposables.clear()
                }
            }
        }
    }


}