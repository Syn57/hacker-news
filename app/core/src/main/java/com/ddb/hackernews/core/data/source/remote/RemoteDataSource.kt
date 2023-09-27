package com.ddb.hackernews.core.data.source.remote

import android.content.res.Resources
import android.util.Log
import com.ddb.hackernews.core.data.Resource
import com.ddb.hackernews.core.data.source.remote.network.ApiResponse
import com.ddb.hackernews.core.data.source.remote.network.ApiService
import com.ddb.hackernews.core.data.source.remote.response.CommentsResponse
import com.ddb.hackernews.core.data.source.remote.response.NewsResponse
import com.ddb.hackernews.core.domain.model.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllNews(): Flow<ApiResponse<List<NewsResponse>>> {
        //get data from remote api
        return flow {
            try {
                val responseNewsList = apiService.getTopNews()
                val newsList = ArrayList<NewsResponse>()
                Log.d("TAG", "getAllNews: $responseNewsList")
                var count = 0
                responseNewsList.forEach {
                    if (count < 25){
                        val news = apiService.getNews("$it")
                        Log.d("TAG", "getAllNews2: ${news.score}")
                        newsList.add(news)
                        count++
                    }
                }
                if (newsList.isNotEmpty()){
                    emit(ApiResponse.Success(newsList))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllComments(idComments: List<Int?>?): Flow<Resource<List<CommentsResponse>>>{
        return flow {
            try {
                val result = ArrayList<CommentsResponse>()
                idComments?.forEach {
                    val response = apiService.getComments(it.toString())
                    result.add(response)
                }
                Log.d("TAG", "getAllComments2: $result")
                if(result.isNotEmpty()){
                    emit(Resource.Success(result.toList()))
                }
            } catch (e: Exception){
                emit(Resource.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }



}