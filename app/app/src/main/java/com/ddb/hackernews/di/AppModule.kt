package com.ddb.hackernews.di

import com.ddb.hackernews.core.domain.usecase.NewsInteractor
import com.ddb.hackernews.core.domain.usecase.NewsUseCase
import com.ddb.hackernews.viewmodel.DetailNewsViewModel
import com.ddb.hackernews.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}
//
val viewModelModule = module {
    viewModel { DetailNewsViewModel(get()) }
//    viewModel { FavoriteViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}