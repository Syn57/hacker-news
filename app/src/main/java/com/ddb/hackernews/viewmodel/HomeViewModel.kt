package com.ddb.hackernews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ddb.hackernews.core.domain.usecase.NewsUseCase

class HomeViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    val news = newsUseCase.getAllNews().asLiveData()
    val latestNewsFav = newsUseCase.getLatestNewsFav().asLiveData()

}