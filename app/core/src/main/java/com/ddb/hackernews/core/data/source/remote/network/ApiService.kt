package com.ddb.hackernews.core.data.source.remote.network

import com.ddb.hackernews.core.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

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

}