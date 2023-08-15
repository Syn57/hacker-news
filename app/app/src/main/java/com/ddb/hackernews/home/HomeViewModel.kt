package com.ddb.hackernews.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ddb.hackernews.core.data.Resource
import com.ddb.hackernews.core.domain.model.Comment
import com.ddb.hackernews.core.domain.usecase.NewsUseCase

class HomeViewModel(newsUseCase: NewsUseCase): ViewModel() {
    val news = newsUseCase.getAllNews().asLiveData()

}