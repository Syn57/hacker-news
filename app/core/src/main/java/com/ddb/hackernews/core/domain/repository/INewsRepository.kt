package com.ddb.hackernews.core.domain.repository

import com.ddb.hackernews.core.data.Resource
import com.ddb.hackernews.core.data.source.remote.network.ApiResponse
import com.ddb.hackernews.core.data.source.remote.response.CommentsResponse
import com.ddb.hackernews.core.domain.model.Comment
import com.ddb.hackernews.core.domain.model.News
import kotlinx.coroutines.flow.Flow


interface INewsRepository {
    fun getAllNews(): Flow<Resource<List<News>>>

    fun getFavoriteNews(): Flow<List<News>>

    fun setFavoriteNews(news: News, state: Boolean)

    fun getAllComments(idComments: List<Int>): Flow<Resource<List<Comment>>>
}