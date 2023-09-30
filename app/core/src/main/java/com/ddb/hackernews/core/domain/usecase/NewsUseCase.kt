package com.ddb.hackernews.core.domain.usecase


import com.ddb.hackernews.core.data.Resource
import com.ddb.hackernews.core.data.source.remote.response.CommentsResponse
import com.ddb.hackernews.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getAllNews(): Flow<Resource<List<News>>>
    fun getLatestNewsFav(): Flow<News?>
    fun getFavoriteNews(): Flow<List<News>?>
    fun setFavoriteNews(news: News?, state: Boolean)
    fun getIsFav(id: Int): Flow<Boolean>
    suspend fun getAllComments(idComments: List<Int?>?): Flow<Resource<List<CommentsResponse>>>

}