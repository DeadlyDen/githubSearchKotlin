package com.githubsearchkotlin.base.repository

import io.reactivex.disposables.CompositeDisposable

interface BaseRepository<R> {

    fun query(specification: Specification<R>)
}