package com.gunishjain.newsapp.data.repository

import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }

    fun getNewsEverything(sourceId: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsEverything(sourceId))
        }.map {
            it.articles
        }
    }

    fun getNewsSources(): Flow<List<Source>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.sources
        }
    }

    fun getNewsByCountry(countryId: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsCountry(countryId))
        }.map {
            it.articles
        }
    }

    fun getNewsByLanguage(languageId: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsLanguage(languageId))
        }.map {
            it.articles
        }
    }

    fun getSearchResult(query: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getSearchResult(query))
        }.map {
            it.articles
        }
    }
}