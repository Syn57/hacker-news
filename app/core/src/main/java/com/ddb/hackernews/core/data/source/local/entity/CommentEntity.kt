package com.ddb.hackernews.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "parent")
    val parent: Int?,
    @ColumnInfo(name = "by")
    val by: String?,
    @ColumnInfo(name = "text")
    val text: String?,
    @ColumnInfo(name = "time")
    val time: Int?,
    @ColumnInfo(name = "type")
    val type: String?,
    @ColumnInfo(name = "kids")
    val kids: List<Int?>?
)