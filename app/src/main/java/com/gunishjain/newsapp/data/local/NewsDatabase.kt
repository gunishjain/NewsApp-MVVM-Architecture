package com.gunishjain.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gunishjain.newsapp.data.local.dao.ArticleDao
import com.gunishjain.newsapp.data.local.entity.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}