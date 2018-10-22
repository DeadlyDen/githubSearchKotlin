package com.githubsearchkotlin.domain

import com.githubsearchkotlin.base.repository.BaseNetworkSpecification
import com.githubsearchkotlin.base.repository.BaseRepository
import com.githubsearchkotlin.base.repository.RepositoryCallBack
import com.githubsearchkotlin.base.repository.Specification
import com.githubsearchkotlin.data.model.RepositoryItem
import com.githubsearchkotlin.data.model.SearchRepoResponse
import io.reactivex.disposables.CompositeDisposable

class GithubNetworkRepositoryItemImpl : BaseRepository<SearchRepoResponse, RepositoryItem> {

    lateinit var callback: RepositoryCallBack<SearchRepoResponse>

    private var disposables: CompositeDisposable = CompositeDisposable()

    override fun query(specification: Specification<SearchRepoResponse>): List<RepositoryItem> {
        val baseNetworkSpecification: BaseNetworkSpecification<SearchRepoResponse> = specification as BaseNetworkSpecification<SearchRepoResponse>
        return baseNetworkSpecification.query().items
    }

}

    //    override fun query(specification: Specification) {
//        when (specification) {
//            is GithubItemsSpecification -> {
//                disposables.add(specification.query()
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribeWith(object : DisposableObserver<SearchRepoResponse>() {
//                            override fun onNext(t: SearchRepoResponse) {
//                                callback.onSuccess(t, specification.loadMore)
//                            }
//
//                            override fun onError(e: Throwable) {
//                                callback.onFailure(e)
//                            }
//
//                            override fun onComplete() {
//
//                            }
//                        }))
//                specification.lastPage = specification.page
//            }
//            is GithubORMSpecification -> {
//                disposables.add(specification.query()
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribeWith(object : DisposableObserver<SearchRepoResponse>() {
//                            override fun onNext(t: SearchRepoResponse) {
//                                callback.onSuccess(t, false)
//                            }
//
//                            override fun onError(e: Throwable) {
//                                callback.onFailure(e)
//                            }
//
//                            override fun onComplete() {
//
//                            }
//                        }))
//            }
//        }
//    }

//    fun stopSearch(specification: Specification) {
//        when (specification) {
//            is GithubItemsSpecification -> {
//                if (disposables.size() != 0) {
//                    disposables.clear()
//                }
//            }
//        }
//    }

