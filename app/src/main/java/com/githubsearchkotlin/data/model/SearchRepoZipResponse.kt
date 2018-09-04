package com.githubsearchkotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by den on 30.06.18.
 */

 class SearchRepoZipResponse(var searchRepoResponse: SearchRepoResponse,
                                 var searchRepoResponse2: SearchRepoResponse) : BaseModel() , Serializable
