package com.ddb.hackernews.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddb.hackernews.data.CommentsResponse
import com.ddb.hackernews.data.Story
import com.ddb.hackernews.data.StoryResponse
import com.ddb.hackernews.repository.StoryRepository
import com.ddb.hackernews.retrofit.ApiConfig
import com.ddb.hackernews.retrofit.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryViewModel(application: Application) : ViewModel() {

    private val mStoryRepository: StoryRepository = StoryRepository(application, ApiConfig.getData())

    private val _comments = MutableLiveData<ArrayList<CommentsResponse>>()
    val comments: LiveData<ArrayList<CommentsResponse>> = _comments

    var tempArray: ArrayList<CommentsResponse> = arrayListOf()

    var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    fun getComments(idComment: List<Int>) {
        GlobalScope.launch {
            _isLoading.postValue(true)
            idComment.forEach {
                getEachComment(it)
            }
            _isLoading.postValue(false)
        }
    }

    private fun getEachComment(commentId: Int) {
        GlobalScope.launch {
            val commentPath = "$commentId.json"
            val client = ApiConfig.getData().getComment(commentPath)
            client.enqueue(object : Callback<CommentsResponse> {
                override fun onResponse(
                    call: Call<CommentsResponse>,
                    response: Response<CommentsResponse>
                ) {
                    if (response.isSuccessful) {
                        tempArray.add(response.body()!!)
                        _comments.postValue(tempArray)
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<CommentsResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }

    fun insert(story: StoryResponse?) {
        mStoryRepository.insertStory(story)
    }

    fun update(story: StoryResponse?) {
        mStoryRepository.update(story)
    }

    fun getIsFav(idStory: Int): LiveData<Boolean> = mStoryRepository.getIsFav(idStory)

    companion object {
        private const val TAG = "DetailStoryViewModel"
    }
}