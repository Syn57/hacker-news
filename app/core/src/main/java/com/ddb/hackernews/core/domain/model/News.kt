package com.ddb.hackernews.core.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class News (
    val id: Int,
    val score: Int?,
    val idStory: Int?,
    val title: String?,
    val by: String?,
    val time: Int?,
    val url: String?,
    val kids: List<Int?>?,
    val isFav: Boolean?
): Parcelable