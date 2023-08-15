package com.ddb.hackernews.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ddb.hackernews.core.data.source.local.entity.NewsEntity
import com.ddb.hackernews.core.data.source.remote.response.StoryResponse
import com.ddb.hackernews.core.domain.model.News

@Dao
interface StoryDao {
    @Query("SELECT * FROM favorite WHERE is_fav = 1 ORDER BY ID DESC LIMIT 1")
    fun getLastFav(): NewsEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: StoryResponse?)

    @Query("DELETE FROM favorite WHERE id = :idStory")
    fun deleteUser(idStory: Int)

    @Query("SELECT is_fav FROM favorite WHERE id = :idStory")
    fun getIsFav(idStory: Int): LiveData<Boolean>

    @Query("SELECT * FROM favorite ORDER BY ID DESC")
    fun getAllStories(): List<StoryResponse>?

    @Update
    fun update(story: StoryResponse?)

//    @Query("SELECT * FROM news ")
//    fun getAllNews(): LiveData<List<NewsEntity>>
//
//    @Query("SELECT * FROM news where is_fav = 1")
//    fun getFavoriteNews(): LiveData<List<NewsEntity>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertNews(news: List<NewsEntity>)
//
//    @Update
//    fun updateFavoriteNews(news: NewsEntity)
}