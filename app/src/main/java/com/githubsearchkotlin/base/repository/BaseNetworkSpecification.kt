package com.githubsearchkotlin.base.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


interface BaseNetworkSpecification<T> : Specification {
    fun getQuery() : Observable<T>
}