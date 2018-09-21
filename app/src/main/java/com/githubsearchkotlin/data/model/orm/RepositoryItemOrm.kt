package com.githubsearchkotlin.data.model.orm

import com.githubsearchkotlin.data.local.AppDatabase
import com.githubsearchkotlin.data.model.RepositoryItem
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, allFields = true)
data class RepositoryItemOrm constructor(@PrimaryKey var id: Int = 0, @Column var name: String = "", @Column var url: String = "", @Column var isViewed : Boolean = false) : BaseModel() {

    fun toPOJO(): RepositoryItem {
        return RepositoryItem(id, name, url, isViewed)
    }
}