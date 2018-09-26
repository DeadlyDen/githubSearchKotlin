package com.githubsearchkotlin.data.local

import android.content.Context
import com.githubsearchkotlin.data.localPreferencesHelper.PreferencesHelper
import com.githubsearchkotlin.data.model.RepositoryItem
import com.githubsearchkotlin.data.model.SearchRepoResponse
import com.githubsearchkotlin.data.model.orm.RepositoryItemDB
import com.githubsearchkotlin.data.model.orm.RepositoryItemDB_Table
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.Delete
import com.raizlabs.android.dbflow.sql.language.Select
import com.raizlabs.android.dbflow.sql.language.Update
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class DatabaseHelper(context: Context, preferencesHelper: PreferencesHelper) {


    init {
        FlowManager.init(context)
    }

    fun saveRepositoryItemPOJO(repositoryItem: RepositoryItem, position: Int) {
        RepositoryItemDB(repositoryItem.id, repositoryItem.name, repositoryItem.url, repositoryItem.isViewed, position).save()
    }

    fun deleteRepositoryItem(id: Int) {
        Delete().from(RepositoryItemDB::class.java).where(RepositoryItemDB_Table.id.`is`(id)).execute()
    }

    fun deleteRepositoryItems() {
        Delete().from(RepositoryItemDB::class.java).async().execute()
    }

    fun getRepositoryItems() : Observable<SearchRepoResponse> {
        val items: ArrayList<RepositoryItem> = ArrayList()
        Select().from(RepositoryItemDB::class.java).orderBy(RepositoryItemDB_Table.position, true).queryList().forEach { item -> items.add(item.toPOJO()) }
        return Observable.just(SearchRepoResponse(items))
    }

    fun getResentSearchRepoId(): ArrayList<Int> {
        val recentSearchId: ArrayList<Int> = ArrayList()
        Select(RepositoryItemDB_Table.id).from(RepositoryItemDB::class.java).queryList().forEach { repositoryItemDB: RepositoryItemDB? ->
            recentSearchId.add(repositoryItemDB!!.id)
        }
        return recentSearchId
    }

    fun getRecentSearchUrl(id : Int) : String? {
        return Select(RepositoryItemDB_Table.url).from(RepositoryItemDB::class.java).where(RepositoryItemDB_Table.id.`is`(id)).querySingle()?.url
    }

    fun updatePositionRecentSearchItem(id : Int, position: Int) {
       Update(RepositoryItemDB::class.java).set(RepositoryItemDB_Table.position.eq(position)).where(RepositoryItemDB_Table.id.`is`(id)).async().execute()
    }
}
