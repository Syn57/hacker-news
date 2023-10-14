package com.ddb.hackernews.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ddb.hackernews.core.data.source.local.entity.NewsEntity
import com.ddb.hackernews.core.utils.DataConverter

@TypeConverters(DataConverter::class)
@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}