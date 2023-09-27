package com.ddb.hackernews.core.domain.usecase

import com.ddb.hackernews.core.data.source.remote.network.ApiResponse
import com.ddb.hackernews.core.data.source.remote.response.CommentsResponse
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class NewsInteractor(private val newsRepository: INewsRepository): NewsUseCase {

    override fun getAllNews() = newsRepository.getAllNews()

    override fun getFavoriteNews() = newsRepository.getFavoriteNews()

    override fun setFavoriteNews(news: News, state: Boolean) = newsRepository.setFavoriteNews(news, state)
    override suspend fun getAllComments(idComments: List<Int?>?) = newsRepository.getAllComments(idComments)
}