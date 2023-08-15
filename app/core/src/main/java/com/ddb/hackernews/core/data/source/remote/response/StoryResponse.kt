package com.ddb.hackernews.core.data.source.remote.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class StoryResponse(

	@ColumnInfo
	@field:SerializedName("score")
	var score: Int? = null,

	@ColumnInfo
	@field:SerializedName("by")
	var by: String? = null,

	@PrimaryKey
	@ColumnInfo
	@field:SerializedName("id")
	var id: Int? = null,

	@ColumnInfo
	@field:SerializedName("time")
	var time: Int? = null,

	@ColumnInfo
	@field:SerializedName("title")
	var title: String? = null,

	@ColumnInfo
	@field:SerializedName("type")
	var type: String? = null,

	@ColumnInfo
	@field:SerializedName("descendants")
	var descendants: Int? = null,

	@ColumnInfo
	@field:SerializedName("url")
	var url: String? = null,

	@ColumnInfo
	@field:SerializedName("kids")
	var kids: List<Int?>? = null,

	@ColumnInfo(name="is_fav")
	var isFav: Boolean = false

): Parcelable

class DataConverter {

	@TypeConverter
	fun fromCountryLangList(value: List<Int?>?): String? {
		val gson = Gson()
		val type = object : TypeToken<List<Int?>?>() {}.type
		return gson.toJson(value, type)
	}

	@TypeConverter
	fun toCountryLangList(value: String?): List<Int?>? {
		val gson = Gson()
		val type = object : TypeToken<List<Int?>?>() {}.type
		return gson.fromJson(value, type)
	}
}
