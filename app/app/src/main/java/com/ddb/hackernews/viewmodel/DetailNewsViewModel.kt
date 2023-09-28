package com.ddb.hackernews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.ddb.hackernews.core.data.Resource
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.domain.usecase.NewsUseCase

class DetailNewsViewModel(private val newsUseCase: NewsUseCase): ViewModel() {
    fun setFavoriteNews(news: News, newsStatus: Boolean) = newsUseCase.setFavoriteNews(news,newsStatus)
    fun getAllComments(idComments: List<Int?>?) = liveData {
        emit(Resource.Loading())
        emitSource(newsUseCase.getAllComments(idComments).asLiveData())
    }
    fun getIsFav(id: Int) = newsUseCase.getIsFav(id).asLiveData()
}