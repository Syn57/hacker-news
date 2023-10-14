package com.ddb.hackernews.core.data.source.local.room

import androidx.room.*
import com.ddb.hackernews.core.data.source.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news where is_fav = 1")
    fun getFavoriteNews(): Flow<List<NewsEntity>?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Update
    fun updateFavoriteNews(news: NewsEntity)

    @Query("SELECT * FROM news WHERE is_fav = 1 ORDER BY ID DESC LIMIT 1")
    fun getLastNewsFav(): Flow<NewsEntity?>

    @Query("SELECT is_fav FROM news WHERE id_story = :id")
    fun getIsFav(id: Int): Flow<Boolean>


}