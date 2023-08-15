package com.ddb.hackernews.core.data.source.local

import androidx.lifecycle.LiveData
import com.ddb.hackernews.core.data.source.local.entity.CommentEntity
import com.ddb.hackernews.core.data.source.local.entity.NewsEntity
import com.ddb.hackernews.core.data.source.local.room.NewsDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val newsDao: NewsDao) {
//    companion object {
//        private var instance: LocalDataSource? = null
//
//        fun getInstance(newsDao: NewsDao): LocalDataSource =
//            instance ?: synchronized(this) {
//                instance ?: LocalDataSource(newsDao)
//            }
//    }
//
//    fun getAllNews(): LiveData<List<NewsEntity>> = newsDao.getAllNews()
//
//    fun getFavoriteNews(): LiveData<List<NewsEntity>> = newsDao.getFavoriteNews()
//
//    fun insertNews(newsList: List<NewsEntity>) = newsDao.insertNews(newsList)
//
//    fun setFavoriteNews(news: NewsEntity, newState: Boolean) {
//        news.isFav = newState
//        newsDao.updateFavoriteNews(news)
//    }
    fun getAllNews(): Flow<List<NewsEntity>> = newsDao.getAllNews()

    fun getFavoriteNews(): Flow<List<NewsEntity>> = newsDao.getFavoriteNews()

    suspend fun insertNews(newsList: List<NewsEntity>) = newsDao.insertNews(newsList)

    fun setFavoriteNews(news: NewsEntity, newState: Boolean) {
        news.isFav = newState
        newsDao.updateFavoriteNews(news)
    }

    fun getAllComments(): Flow<List<CommentEntity>> = newsDao.getAllComments()

    suspend fun insertComment(newComments: List<CommentEntity>) = newsDao.insertComment(newComments)

    suspend fun deleteComments() = newsDao.deleteAllComment()
}