package com.ddb.hackernews.core.domain.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ddb.hackernews.core.data.source.local.entity.NewsEntity
import com.ddb.hackernews.core.data.source.remote.response.StoryResponse
import com.ddb.hackernews.core.data.source.local.room.StoryDao
import com.ddb.hackernews.core.data.source.local.room.StoryDatabase
import com.ddb.hackernews.core.data.source.remote.network.ApiConfig
import com.ddb.hackernews.core.data.source.remote.network.ApiService
import retrofit2.await
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StoryRepository(
    application: Application,
    apiConfig: ApiService
) {
    private val mStoryDao: StoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val myApiConfig: ApiService = apiConfig

    init {
        val db = StoryDatabase.getDatabase(application)
        mStoryDao = db.storyDao()
    }

    suspend fun getStory(path: String): StoryResponse {

//        return apiConfig.getData().getStory(path).await()
        val myStoryResponse = myApiConfig.getStory(path).await()
        mStoryDao.insertUser(myStoryResponse)
        return myStoryResponse
//        return apiService.getStory(path).await()
    }

    suspend fun getListStory(): List<Int> {
        return ApiConfig.getData().getTop().await()
    }

    fun getLastFav(): NewsEntity? = mStoryDao.getLastFav()

    fun insertStory(story: StoryResponse?) {
        executorService.execute { mStoryDao.insertUser(story) }
    }

    fun deleteStory(idStory: Int) {
        executorService.execute { mStoryDao.deleteUser(idStory) }
    }

    fun update(story: StoryResponse?){
        executorService.execute { mStoryDao.update(story) }
    }

    fun getIsFav(idStory: Int): LiveData<Boolean> = mStoryDao.getIsFav(idStory)

    fun getAllStory(): List<StoryResponse>? = mStoryDao.getAllStories()

}