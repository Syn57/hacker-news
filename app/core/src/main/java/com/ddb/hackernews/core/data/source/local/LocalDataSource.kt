package com.ddb.hackernews.core.data.source.local


import com.ddb.hackernews.core.data.source.local.entity.NewsEntity
import com.ddb.hackernews.core.data.source.local.room.NewsDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val newsDao: NewsDao) {
    fun getAllNews(): Flow<List<NewsEntity>> = newsDao.getAllNews()

    fun getFavoriteNews(): Flow<List<NewsEntity>?> = newsDao.getFavoriteNews()

    suspend fun insertNews(newsList: List<NewsEntity>) = newsDao.insertNews(newsList)

    fun setFavoriteNews(news: NewsEntity, newState: Boolean) {
        news.isFav = newState
        newsDao.updateFavoriteNews(news)
    }
    fun getIsFav(id: Int): Flow<Boolean> = newsDao.getIsFav(id)
    fun getLastNewsFav(): Flow<NewsEntity?> = newsDao.getLastNewsFav()

}