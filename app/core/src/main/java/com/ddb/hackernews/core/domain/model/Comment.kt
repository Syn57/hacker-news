package com.ddb.hackernews.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment (
    val parent: Int?,
    val by: String?,
    val id: Int?,
    val text: String?,
    val time: Int?,
    val type: String?,
    val kids: List<Int?>?
): Parcelable