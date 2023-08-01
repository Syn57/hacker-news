package com.ddb.hackernews.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.*
import com.ddb.hackernews.data.Story
import com.ddb.hackernews.data.StoryResponse
import com.ddb.hackernews.repository.StoryRepository
import com.ddb.hackernews.retrofit.ApiConfig
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mStoryRepository: StoryRepository = StoryRepository(application, ApiConfig.getData())

    private val _stories = MutableLiveData<ArrayList<StoryResponse>?>()
    val stories: LiveData<ArrayList<StoryResponse>?> = _stories

    private val _story = MutableLiveData<Story?>()
    val story: MutableLiveData<Story?> = _story

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

    private fun getLastFav(): Story? = mStoryRepository.getLastFav()

    fun favorite() {
        _story.postValue(getLastFav())
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
