package com.ddb.hackernews.core.data.source.remote.network

import com.ddb.hackernews.core.data.source.remote.response.*
import com.ddb.hackernews.core.domain.model.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("v0/item/{id}")
    fun getStory(
        @Path("id") id: String
    ): Call<StoryResponse>

    @GET("v0/topstories.json")
    suspend fun getTopNews(): List<Int>

    @GET("v0/item/{id}.json")
    suspend fun getNews(
        @Path("id") id: String
    ): NewsResponse

    @GET("v0/item/{id}.json")
    suspend fun getComments(
        @Path("id") id: String
    ): CommentsResponse

    @GET("v0/item/{id}.json")
    fun getComments2(
        @Path("id") id: String
    ): CommentsResponse

    @GET("v0/item/{id}")
    fun getComment(
        @Path("id") id: String
    ): Call<CommentsResponse>

    @GET("v0/topstories.json")
    fun getTop(): Call<List<Int>>

    @GET("v0/topstories.json")
    fun test(): TopListNewsResponse
}