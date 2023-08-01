package com.ddb.hackernews.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ddb.hackernews.data.Story
import com.ddb.hackernews.data.StoryResponse

@Dao
interface StoryDao {
    @Query("SELECT * FROM favorite WHERE is_fav = 1 ORDER BY ID DESC LIMIT 1")
    fun getLastFav(): Story?

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
}