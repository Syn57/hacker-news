package com.ddb.hackernews.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @field:SerializedName("score")
    var score: Int? = null,

    @field:SerializedName("by")
    var by: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("time")
    var time: Int? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("type")
    var type: String? = null,

    @field:SerializedName("descendants")
    var descendants: Int? = null,

    @field:SerializedName("url")
    var url: String? = null,

    @field:SerializedName("kids")
    var kids: List<Int?>? = null

)