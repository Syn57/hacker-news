package com.ddb.hackernews.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ddb.hackernews.core.data.source.local.entity.CommentEntity
import com.ddb.hackernews.core.data.source.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news where is_fav = 1")
    fun getFavoriteNews(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Update
    fun updateFavoriteNews(news: NewsEntity)

    @Query("SELECT * FROM comments ")
    fun getAllComments(): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(news: List<CommentEntity>)

    @Query("DELETE FROM comments")
    suspend fun deleteAllComment()
}