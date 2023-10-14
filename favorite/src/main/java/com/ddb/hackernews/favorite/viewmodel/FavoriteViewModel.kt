package com.ddb.hackernews.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ddb.hackernews.core.domain.usecase.NewsUseCase

class FavoriteViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    val newsFav = newsUseCase.getFavoriteNews().asLiveData()

}