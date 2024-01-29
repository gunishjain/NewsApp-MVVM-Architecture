package com.gunishjain.newsapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.local.ArticleDatabase
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.model.Source
import com.gunishjain.newsapp.utils.AppConstant.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val networkService: NetworkService,
    private val articleDatabase: ArticleDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getTopHeadlines(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            remoteMediator = ArticleRemoteMediator(
                networkService,
                articleDatabase
            ),
            pagingSourceFactory = {
                articleDatabase.articleDao().getAllArticles()
            }
        ).flow
    }

    fun getNewsEverything(sourceId: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                SourceNewsPagingSource(networkService, sourceId = sourceId)
            }
        ).flow
    }

    fun getNewsSources(): Flow<List<Source>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.sources
        }
    }

    fun getNewsByCountry(countryIdOne: String, countryIdTwo: String?): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                CountryNewsPagingSource(networkService, countryIdOne, countryIdTwo)
            }
        ).flow
    }

    fun getNewsByLanguage(
        languageIdOne: String,
        languageIdTwo: String?
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                LanguageNewsPagingSource(networkService, languageIdOne, languageIdTwo)
            }
        ).flow
    }

    fun getSearchResult(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                SearchNewsPagingSource(networkService, query)
            }
        ).flow
    }
}