package com.githubsearchkotlin.base.repository


interface BaseNetworkSpecification<R> : Specification<R> {
    fun query() : R
}