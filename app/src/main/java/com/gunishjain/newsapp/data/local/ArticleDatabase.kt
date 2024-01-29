package com.gunishjain.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gunishjain.newsapp.data.local.dao.ArticleDao
import com.gunishjain.newsapp.data.local.dao.ArticleRemoteKeysDao
import com.gunishjain.newsapp.data.local.entity.ArticleRemoteKeys
import com.gunishjain.newsapp.data.model.Article

@Database(entities = [Article::class, ArticleRemoteKeys::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun articleRemoteKeysDao(): ArticleRemoteKeysDao

}