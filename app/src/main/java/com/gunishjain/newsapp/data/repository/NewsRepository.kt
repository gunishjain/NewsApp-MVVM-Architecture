package com.gunishjain.newsapp.data.repository

import ApiArticle
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.local.DatabaseService
import com.gunishjain.newsapp.data.local.NewsDatabaseService
import com.gunishjain.newsapp.data.local.entity.Article
import com.gunishjain.newsapp.data.model.ApiSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import toArticleEntity
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getTopHeadlines(country: String): Flow<List<Article>> {
        return flow { emit(networkService.getTopHeadlines(country))
        }.map {
            it.apiArticles.map { apiArticle -> apiArticle.toArticleEntity() }
        }.flatMapConcat { articles ->
            flow {emit(databaseService.deleteAllAndInsertAll(articles))
            }.flatMapConcat {
                databaseService.getArticles()
            }
        }
    }

    fun getArticlesDirectlyFromDB(): Flow<List<Article>> {
        return databaseService.getArticles()
    }

    fun getNewsEverything(sourceId: String): Flow<List<ApiArticle>> {
        return flow {
            emit(networkService.getNewsEverything(sourceId))
        }.map {
            it.apiArticles
        }
    }

    fun getNewsSources(): Flow<List<ApiSource>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.apiSources
        }
    }

    fun getNewsByCountry(countryId: String): Flow<List<ApiArticle>> {
        return flow {
            emit(networkService.getNewsCountry(countryId))
        }.map {
            it.apiArticles
        }
    }

    fun getNewsByLanguage(languageId: String): Flow<List<ApiArticle>> {
        return flow {
            emit(networkService.getNewsLanguage(languageId))
        }.map {
            it.apiArticles
        }
    }

    fun getSearchResult(query: String): Flow<List<ApiArticle>> {
        return flow {
            emit(networkService.getSearchResult(query))
        }.map {
            it.apiArticles
        }
    }
}