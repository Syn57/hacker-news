package com.ddb.hackernews.core.utils

import com.ddb.hackernews.core.data.source.local.entity.NewsEntity
import com.ddb.hackernews.core.data.source.remote.response.CommentsResponse
import com.ddb.hackernews.core.data.source.remote.response.NewsResponse
import com.ddb.hackernews.core.domain.model.Comment
import com.ddb.hackernews.core.domain.model.News


object DataMapper {
    fun mapResponsesToEntities(input: List<NewsResponse>): List<NewsEntity> {
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

    fun mapResponsesToEntitiesComment(input: List<CommentsResponse>?): List<Comment> {
        val commentsList = ArrayList<Comment>()
        input?.map {
            val comment = Comment(
                parent = it.parent,
                by = it.by,
                id = it.id,
                text = it.text,
                time = it.time,
                type = it.type,
                kids = it.kids
            )
            commentsList.add(comment)
        }
        return commentsList
    }

    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map {
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
        return input?.map {
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


    fun mapDomainToEntity(input: News?) = NewsEntity(
        id = input?.id ?: 0,
        idStory = input?.idStory ?: 0,
        title = input?.title ?: "",
        by = input?.by ?: "",
        url = input?.url ?: "",
        time = input?.time ?: 0,
        score = input?.score ?: 0,
        kids = input?.kids ?: listOf(),
        isFav = input?.isFav ?: false
    )

}