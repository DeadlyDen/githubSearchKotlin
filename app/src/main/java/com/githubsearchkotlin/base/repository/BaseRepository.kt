package com.githubsearchkotlin.base.repository

import com.githubsearchkotlin.data.model.BaseModel

interface BaseRepository<R, out T : BaseModel> {
    fun query(specification: Specification<R>) : List<T>
}