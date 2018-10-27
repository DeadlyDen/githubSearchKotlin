package com.githubsearchkotlin.base.repository

import io.reactivex.disposables.CompositeDisposable

interface BaseRepository<R> {
    var disposables: CompositeDisposable
        get() = CompositeDisposable()
        set(value) = TODO()

    fun query(specification: Specification<R>)
}