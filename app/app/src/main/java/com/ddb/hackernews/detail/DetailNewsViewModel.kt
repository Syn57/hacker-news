package com.ddb.hackernews.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.domain.usecase.NewsUseCase

class DetailNewsViewModel(private val newsUseCase: NewsUseCase): ViewModel() {
    fun setFavoriteNews(news: News, newsStatus: Boolean) = newsUseCase.setFavoriteNews(news,newsStatus)
    fun getAllComments(idComments: List<Int>) = newsUseCase.getAllComments(idComments).asLiveData()
}