package com.ddb.hackernews.core.domain.usecase

import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.domain.repository.INewsRepository

class NewsInteractor(private val newsRepository: INewsRepository): NewsUseCase {

    override fun getAllNews() = newsRepository.getAllNews()
    override fun getLatestNewsFav() = newsRepository.getLatestNewsFav()
    override fun getFavoriteNews() = newsRepository.getFavoriteNews()
    override fun getIsFav(id: Int) = newsRepository.getIsFav(id)
    override fun setFavoriteNews(news: News?, state: Boolean) = newsRepository.setFavoriteNews(news, state)
    override suspend fun getAllComments(idComments: List<Int?>?) = newsRepository.getAllComments(idComments)
}