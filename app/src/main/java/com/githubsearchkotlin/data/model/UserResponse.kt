package com.githubsearchkotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by den on 30.06.18.
 */

 class UserResponse : BaseModel() ,Serializable {

    @SerializedName("login")
    @Expose
    var login: String? = null

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("avatar_url")
    @Expose
    var avatar: String? = null
}
