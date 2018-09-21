package com.githubsearchkotlin.data.local

import android.content.Context
import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
import com.githubsearchkotlin.data.model.RepositoryItem
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.data.model.orm.RepositoryItemOrm
import com.githubsearchkotlin.data.model.orm.RepositoryItemOrm_Table
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.Delete
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class DatabaseHelper(context: Context, preferencesHelper: PreferencesHelper) {


    init {
        FlowManager.init(context)
    }

    fun saveRepoitoryItemPOJO(repositoryItem: RepositoryItem) {
        RepositoryItemOrm(repositoryItem.id, repositoryItem.name, repositoryItem.url, repositoryItem.isViewed).save()
    }

    fun deleteRepositoryItem(id: Int) {
        Delete().from(RepositoryItemOrm::class.java).where(RepositoryItemOrm_Table.id.`is`(id)).execute()
    }

    fun deleteRepositoryItems() {
        Delete().from(RepositoryItemOrm::class.java).async().execute()
    }

    fun getRepositoryItems() : Observable<SearchRepoResponse> {
        val items: ArrayList<RepositoryItem> = ArrayList()
        Select().from(RepositoryItemOrm::class.java).queryList().forEach { item -> items.add(item.toPOJO()) }
        return Observable.just(SearchRepoResponse(items))
    }
}
