package com.githubsearchkotlin.base.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class BaseNetworkSpecification : Specification {

     private var disposables: CompositeDisposable = CompositeDisposable()

    fun <T> load(apiObservable : Observable<T> ,
                              disposableObserver: DisposableObserver<T>) {
        disposables.add(apiObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(disposableObserver))
    }

     fun <T> getObserver(repositoryCallBack: RepositoryCallBack<T>): DisposableObserver<T> {
        return object : DisposableObserver<T>() {
            override fun onNext(t: T) {
                repositoryCallBack.onSuccess(t, false)
            }

            override fun onError(e: Throwable) {
                repositoryCallBack.onFailure(e)
            }

            override fun onComplete() {

            }
        }
    }

     fun <T> getMoreObserver(repositoryCallBack: RepositoryCallBack<T>): DisposableObserver<T> {
        return object : DisposableObserver<T>() {
            override fun onNext(t: T) {
                repositoryCallBack.onSuccess(t, true)
            }

            override fun onError(e: Throwable) {
                repositoryCallBack.onFailure(e)
            }

            override fun onComplete() {

            }
        }
    }

    fun clearDisposables() {
        if (disposables.size() != 0) {
            disposables.clear()
        }
    }
}