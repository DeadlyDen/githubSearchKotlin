package com.githubsearchkotlin.base.repository

import io.reactivex.Observable


interface BaseNetworkSpecification<R> : Specification<R> {
    fun query() : Observable<R>
}