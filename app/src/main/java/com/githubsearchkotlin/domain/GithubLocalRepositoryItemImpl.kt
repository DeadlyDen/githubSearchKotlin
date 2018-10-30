package com.githubsearchkotlin.domain

import com.githubsearchkotlin.base.repository.*
import com.githubsearchkotlin.data.model.SearchRepoResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class GithubLocalRepositoryItemImpl : BaseRepository<SearchRepoResponse> {

    var disposables: CompositeDisposable = CompositeDisposable()

    lateinit var callback: RepositoryCallBack<SearchRepoResponse>

    var onMore: Boolean = false

    override fun query(specification: Specification<SearchRepoResponse>) {
        val baseORMSpecification: BaseORMSpecification<SearchRepoResponse> = specification as BaseORMSpecification<SearchRepoResponse>
        disposables.add(baseORMSpecification.query()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<SearchRepoResponse>() {
                    override fun onNext(t: SearchRepoResponse) {
                        callback.onSuccess(t, onMore)
                    }

                    override fun onError(e: Throwable) {
                        callback.onFailure(e)
                    }

                    override fun onComplete() {

                    }
                }))
    }

}

