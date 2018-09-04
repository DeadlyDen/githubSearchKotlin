package com.githubsearchkotlin.base.viper

import com.githubsearchkotlin.data.model.BaseModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class Interactor<T : BaseModel> {

    private var disposables: CompositeDisposable = CompositeDisposable()

    lateinit var callback: InteractorCallback<T>

    protected abstract fun getApiObservable(): Observable<T>

    protected fun fetch() {
       disposables.add(getApiObservable()
               .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(getObserver()))
    }

    protected fun fetchMore() {
        disposables.add(getApiObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(getMoreObserver()))
    }

    private fun getObserver(): DisposableObserver<T> {
        return object : DisposableObserver<T>() {
            override fun onNext(t: T) {
                callback.onSuccess(t, false)
            }

            override fun onError(e: Throwable) {
                callback.onFailure(e)
            }

            override fun onComplete() {

            }
        }
    }

    private fun getMoreObserver(): DisposableObserver<T> {
        return object : DisposableObserver<T>() {
            override fun onNext(t: T) {
                callback.onSuccess(t, true)
            }

            override fun onError(e: Throwable) {
                callback.onFailure(e)
            }

            override fun onComplete() {

            }
        }
    }

}