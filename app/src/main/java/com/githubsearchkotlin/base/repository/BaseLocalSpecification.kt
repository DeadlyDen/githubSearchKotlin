package com.githubsearchkotlin.base.repository

import io.reactivex.Observable

interface BasekLocalSpecification<T> : Specification {
    fun getQuery() : Observable<T>
}