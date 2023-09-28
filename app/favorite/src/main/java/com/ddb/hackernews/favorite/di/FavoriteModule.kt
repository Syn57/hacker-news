package com.ddb.hackernews.favorite.di

import com.ddb.hackernews.favorite.viewmodel.FavoriteViewModel
import com.ddb.hackernews.viewmodel.DetailNewsViewModel
import com.ddb.hackernews.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favModule = module {
    viewModel { FavoriteViewModel(get()) }
}