package com.gunishjain.newsapp.data.repository

import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor (private val networkService: NetworkService) {

    fun getTopHeadlines(country : String) : Flow<List<Article>> {

        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }

    }

    fun getNewsSources() : Flow<List<Source>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.sources
        }
    }
}