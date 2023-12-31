package com.ddb.hackernews.core.data

import com.ddb.hackernews.core.data.source.local.LocalDataSource
import com.ddb.hackernews.core.data.source.remote.RemoteDataSource
import com.ddb.hackernews.core.data.source.remote.network.ApiResponse
import com.ddb.hackernews.core.data.source.remote.response.NewsResponse
import com.ddb.hackernews.core.domain.model.Comment
import com.ddb.hackernews.core.domain.model.News
import com.ddb.hackernews.core.domain.repository.INewsRepository
import com.ddb.hackernews.core.utils.AppExecutors
import com.ddb.hackernews.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : INewsRepository {
    override fun getAllNews(): Flow<Resource<List<News>>> =
        object : NetworkBoundResource<List<News>, List<NewsResponse>>() {
            override fun loadFromDB(): Flow<List<News>> {
                return localDataSource.getAllNews().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun shouldFetch(data: List<News>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<NewsResponse>>> =
                remoteDataSource.getAllNews()

            override suspend fun saveCallResult(data: List<NewsResponse>) {
                val newsList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }
        }.asFlow()

    override fun getFavoriteNews(): Flow<List<News>?> {
        return localDataSource.getFavoriteNews().map {
            DataMapper.mapEntitiesToDomainFav(it)
        }
    }

    override fun getLatestNewsFav(): Flow<News?> {
        return localDataSource.getLastNewsFav().map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun setFavoriteNews(news: News?, state: Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute { localDataSource.setFavoriteNews(newsEntity, state) }
    }

    override fun getIsFav(id: Int): Flow<Boolean> {
        return localDataSource.getIsFav(id)
    }

    override suspend fun getAllComments(idComments: List<Int?>?): Flow<Resource<List<Comment>>> {
        val response = remoteDataSource.getAllComments(idComments).first()
        return remoteDataSource.getAllComments(idComments).map {
            Resource.Success(DataMapper.mapResponsesToEntitiesComment(response.data))
        }
    }
}