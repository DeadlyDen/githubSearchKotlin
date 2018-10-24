package com.githubsearchkotlin.base.repository

import io.reactivex.Observable

interface BaseORMSpecification<R> : Specification<R> {
    fun query() : Observable<R>
}