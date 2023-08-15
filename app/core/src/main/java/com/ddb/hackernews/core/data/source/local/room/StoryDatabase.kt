package com.ddb.hackernews.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ddb.hackernews.core.data.source.remote.response.DataConverter
import com.ddb.hackernews.core.data.source.remote.response.StoryResponse

@Database(entities = [StoryResponse::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase {
            if (INSTANCE == null) {
                synchronized(StoryDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StoryDatabase::class.java,
                        "story_database"
                    ).addMigrations().build()
                }
            }
            return INSTANCE as StoryDatabase
        }
    }
}