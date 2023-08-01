package com.ddb.hackernews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ddb.hackernews.data.DataConverter
import com.ddb.hackernews.data.Story
import com.ddb.hackernews.data.StoryResponse

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