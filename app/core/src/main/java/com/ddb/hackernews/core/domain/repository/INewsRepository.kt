package com.ddb.hackernews.core.domain.repository

import com.ddb.hackernews.core.data.Resource
import com.ddb.hackernews.core.data.source.remote.network.ApiResponse
import com.ddb.hackernews.core.data.source.remote.response.CommentsResponse
import com.ddb.hackernews.core.domain.model.Comment
import com.ddb.hackernews.core.domain.model.News
import kotlinx.coroutines.flow.Flow


interface INewsRepository {
    fun getAllNews(): Flow<Resource<List<News>>>

    fun getIsFav(id: Int): Flow<Boolean>

    fun getFavoriteNews(): Flow<List<News>?>

    fun getLatestNewsFav(): Flow<News?>

    fun setFavoriteNews(news: News, state: Boolean)

    suspend fun getAllComments(idComments: List<Int?>?): Flow<Resource<List<CommentsResponse>>>
}