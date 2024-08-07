package com.loc.newsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deepen.newsapp.data.models.Article


@Database(entities = [Article::class],version = 1,)
@TypeConverters(NewsTypeConvertor::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var Instance: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NewsDatabase::class.java, "item_database").addTypeConverter(NewsTypeConvertor())
                    .build().also { Instance = it }
            }
        }
    }
}

