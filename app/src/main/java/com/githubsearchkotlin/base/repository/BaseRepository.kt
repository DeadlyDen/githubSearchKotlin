package com.githubsearchkotlin.base.repository

import com.githubsearchkotlin.data.model.BaseModel

interface BaseRepository<R> {
    fun query(specification: Specification<R>)
}