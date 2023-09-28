package com.ddb.hackernews.core.utils

import com.ddb.hackernews.core.data.source.local.entity.NewsEntity

import com.ddb.hackernews.core.data.source.remote.response.NewsResponse

import com.ddb.hackernews.core.domain.model.News


object DataMapper {
    fun mapResponsesToEntities(input: List<NewsResponse>): List<NewsEntity>{
        val newsList = ArrayList<NewsEntity>()
        input.map {
            val story = NewsEntity(
                idStory = it.id,
                title = it.title,
                by = it.by,
                time = it.time,
                isFav = false,
                url = it.url,
                kids = it.kids,
                score = it.score
            )
            newsList.add(story)
        }
        return newsList
    }
    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map{
            News(
                id = it.id,
                idStory = it.idStory,
                title = it.title,
                by = it.by,
                time = it.time,
                url = it.url,
                isFav = it.isFav,
                score = it.score,
                kids = it.kids
            )
        }

    fun mapEntitiesToDomainFav(input: List<NewsEntity>?): List<News>? {
        return input?.map{
            News(
                id = it.id,
                idStory = it.idStory,
                title = it.title,
                by = it.by,
                time = it.time,
                url = it.url,
                isFav = it.isFav,
                score = it.score,
                kids = it.kids
            )
        }
    }

    fun mapEntityToDomain(input: NewsEntity?): News? {
        return if (input != null) News(
            id = input.id,
            idStory = input.idStory,
            title = input.title,
            by = input.by,
            time = input.time,
            url = input.url,
            isFav = input.isFav,
            kids = input.kids,
            score = input.score
        ) else null
    }


    fun mapDomainToEntity(input: News) = NewsEntity(
        id = input.id,
        idStory = input.idStory,
        title = input.title,
        by = input.by,
        url = input.url,
        time = input.time,
        score = input.score,
        kids = input.kids,
        isFav = input.isFav
    )

}