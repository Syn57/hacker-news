package com.ddb.hackernews.core.utils

import com.ddb.hackernews.core.data.source.local.entity.CommentEntity
import com.ddb.hackernews.core.data.source.local.entity.NewsEntity
import com.ddb.hackernews.core.data.source.remote.response.CommentsResponse
import com.ddb.hackernews.core.data.source.remote.response.NewsResponse
import com.ddb.hackernews.core.data.source.remote.response.StoryResponse
import com.ddb.hackernews.core.domain.model.Comment
import com.ddb.hackernews.core.domain.model.News
import kotlinx.coroutines.NonDisposableHandle.parent

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

    fun mapEntitiesToDomainComments(input: List<CommentEntity>): List<Comment> =
        input.map{
            Comment(
                parent = it.parent,
                by = it.by,
                id = it.id,
                text = it.text,
                time = it.time,
                type = it.type,
                kids = it.kids
            )
        }

    fun mapDomainToEntityComments(input: Comment) = CommentEntity(
        id = input.id,
        parent = input.parent,
        by = input.by,
        text = input.text,
        time = input.time,
        type = input.type,
        kids = input.kids
    )

    fun mapResponsesToEntitiesComments(input: List<CommentsResponse>): List<CommentEntity>{
        val newsList = ArrayList<CommentEntity>()
        input.map {
            val story = CommentEntity(
                parent = it.parent,
                by = it.by,
                id = it.id,
                text = it.text,
                time = it.time,
                type = it.type,
                kids = it.kids
            )
            newsList.add(story)
        }
        return newsList
    }
}