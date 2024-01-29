package com.gunishjain.newsapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gunishjain.newsapp.data.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article_table")
    fun getAllArticles(): PagingSource<Int, Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Query("DELETE FROM article_table")
    suspend fun deleteAll()

}