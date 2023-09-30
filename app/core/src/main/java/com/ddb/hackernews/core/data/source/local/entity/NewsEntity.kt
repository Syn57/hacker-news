package com.ddb.hackernews.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news", indices = [Index(value = ["title"], unique = true)])
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "score")
    var score: Int? = null,
    @ColumnInfo(name = "id_story")
    var idStory: Int? = 0,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "by")
    var by: String? = null,
    @ColumnInfo(name = "url")
    var url: String? = null,
    @ColumnInfo(name = "time")
    var time: Int? = null,
    @ColumnInfo(name = "is_fav")
    var isFav: Boolean? = false,
    @field:SerializedName("kids")
    var kids: List<Int?>? = null
)
