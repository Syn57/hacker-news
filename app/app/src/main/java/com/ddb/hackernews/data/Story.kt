package com.ddb.hackernews.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite1")
@Parcelize
data class Story(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "id_story")
    var idStory: Int? = 0,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "by")
    var by: String? = null,
    @ColumnInfo(name = "time")
    var time: Int? = null,
    @ColumnInfo(name="is_fav")
    var isFav: Boolean = false
): Parcelable