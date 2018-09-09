package com.githubsearchkotlin.base.repository

interface RepositoryCallBack<T> {
    fun onSuccess(model: T, onMore: Boolean)

    fun onFailure(throwable: Throwable)
}