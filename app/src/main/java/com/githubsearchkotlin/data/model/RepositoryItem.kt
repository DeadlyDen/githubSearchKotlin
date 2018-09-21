package com.githubsearchkotlin.data.model

import com.githubsearchkotlin.data.model.orm.RepositoryItemOrm
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by den on 02.07.18.
 */

class RepositoryItem(@field:SerializedName("id")
                     @field:Expose
                     var id: Int, @field:SerializedName("full_name")
                     @field:Expose
                     var name: String, @field:SerializedName("html_url")
                     @field:Expose
                     var url: String, viewed: Boolean) : BaseModel() ,Serializable {

    var isViewed = false

    init {
        this.isViewed = viewed
    }

    fun toORM() : RepositoryItemOrm {
        return RepositoryItemOrm(id, name, url, isViewed)
    }
}
