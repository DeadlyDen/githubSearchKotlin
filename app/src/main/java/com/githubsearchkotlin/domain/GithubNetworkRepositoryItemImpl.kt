package com.githubsearchkotlin.domain

import com.githubsearchkotlin.base.repository.BaseNetworkSpecification
import com.githubsearchkotlin.base.repository.BaseRepository
import com.githubsearchkotlin.base.repository.RepositoryCallBack
import com.githubsearchkotlin.base.repository.Specification
import com.githubsearchkotlin.data.model.SearchRepoResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class GithubNetworkRepositoryItemImpl : BaseRepository<SearchRepoResponse> {

    lateinit var callback: RepositoryCallBack<SearchRepoResponse>

    private var disposables: CompositeDisposable = CompositeDisposable()

    override fun query(specification: Specification<SearchRepoResponse>) {
        val baseNetworkSpecification: BaseNetworkSpecification<SearchRepoResponse> = specification as BaseNetworkSpecification<SearchRepoResponse>
        disposables.add(baseNetworkSpecification.query()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(object : DisposableObserver<SearchRepoResponse>() {
                            override fun onNext(t: SearchRepoResponse) {
                                callback.onSuccess(t, false)
                            }

                            override fun onError(e: Throwable) {
                                callback.onFailure(e)
                            }

                            override fun onComplete() {

                            }
                        }))
//                specification.lastPage = specification.page
    }

}

