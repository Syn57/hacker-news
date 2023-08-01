package com.ddb.hackernews.retrofit

import com.ddb.hackernews.data.CommentsResponse
import com.ddb.hackernews.data.StoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("v0/item/{id}")
    fun getStory(
        @Path("id") id: String
    ): Call<StoryResponse>

    @GET("v0/item/{id}")
    fun getComment(
        @Path("id") id: String
    ): Call<CommentsResponse>

    @GET("v0/topstories.json")
    fun getTop(): Call<List<Int>>
}