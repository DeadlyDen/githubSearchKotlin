package com.githubsearchkotlin.base.repository

interface BaseORMSpecification<R> : Specification<R> {
    fun query() : R
}