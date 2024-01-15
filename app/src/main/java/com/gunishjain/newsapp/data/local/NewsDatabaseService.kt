package com.gunishjain.newsapp.data.local

import com.gunishjain.newsapp.data.local.entity.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsDatabaseService @Inject constructor(private val appDatabase: NewsDatabase) : DatabaseService {
    override fun getArticles(): Flow<List<Article>> {
        return appDatabase.articleDao().getAll()
    }

    override fun deleteAllAndInsertAll(articles: List<Article>) {
        appDatabase.articleDao().deleteAllAndInsertAll(articles)
    }


}