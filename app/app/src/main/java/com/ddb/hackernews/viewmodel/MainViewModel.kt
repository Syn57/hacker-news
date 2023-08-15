package com.ddb.hackernews.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.ddb.hackernews.core.data.source.local.entity.NewsEntity
import com.ddb.hackernews.core.data.source.remote.response.StoryResponse
import com.ddb.hackernews.core.domain.repository.StoryRepository
import com.ddb.hackernews.core.data.source.remote.network.ApiConfig
import kotlinx.coroutines.*


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mStoryRepository: StoryRepository = StoryRepository(application, ApiConfig.getData())

    private val _stories = MutableLiveData<ArrayList<StoryResponse>?>()
    val stories: LiveData<ArrayList<StoryResponse>?> = _stories

    private val _newsEntity = MutableLiveData<NewsEntity?>()
    val newsEntity: MutableLiveData<NewsEntity?> = _newsEntity

    var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    fun getTopStories() {
        GlobalScope.launch {
//            _isLoading.postValue(true)
//            val client = ApiConfig.getData().getTop()
//            client.enqueue(object : Callback<List<Int>> {
//                override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
//                    if (response.isSuccessful) {
//                        response.body()?.forEach { id ->
//                            getTopEachStory(id)
//                        }
//                    } else {
//                        Log.e(TAG, "onFailure: ${response.message()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Int>>, t: Throwable) {
//                    Log.e(TAG, "onFailure: ${t.message}")
//                }
//            })

            _isLoading.postValue(true)
            try {
                if (isInetConnect()){
                    val response = mStoryRepository.getListStory()
                    val temp: ArrayList<StoryResponse>? = arrayListOf()
                    for (i in 0..15){
                        Log.d(TAG, "getTopStories: ${response[i]}")
                        val path = "${response[i]}.json"
                        val eachStory = mStoryRepository.getStory(path)
                        temp?.add(eachStory)
//                        mStoryRepository.insertStory(eachStory)
                    }
                    _stories.postValue(temp)
                    Log.d(TAG, "getTopStories123: $temp")
                    _isLoading.postValue(false)
                } else {
                    val temp: ArrayList<StoryResponse>? = arrayListOf()
                    Log.d(TAG, "getTopStories124: $temp")
                    Log.d(TAG, "getTopStories124: ${mStoryRepository.getAllStory()}")
                    mStoryRepository.getAllStory()?.forEach { story ->
                        temp?.add(story)
                    }
                    _stories.postValue(temp)
                    Log.d(TAG, "getTopStories124: $temp")
                    _isLoading.postValue(false)
                }

            } catch (t: Throwable){
                Log.e(TAG, "getTopStories: Error", )
            }
        }
    }

    private fun isInetConnect(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> return true
                    ConnectivityManager.TYPE_MOBILE -> return true
                    ConnectivityManager.TYPE_ETHERNET -> return true
                    else -> false

                }
            }
        }
        return false
    }

    private fun getLastFav(): NewsEntity? = mStoryRepository.getLastFav()

    fun favorite() {
        _newsEntity.postValue(getLastFav())
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
