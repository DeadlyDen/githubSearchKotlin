package com.githubsearchkotlin.base.repository

interface BaseRepository {
    fun query(specification: Specification)
}