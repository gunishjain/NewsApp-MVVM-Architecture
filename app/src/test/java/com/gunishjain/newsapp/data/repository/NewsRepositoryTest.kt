package com.gunishjain.newsapp.data.repository

import app.cash.turbine.test
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.model.NewsResponse
import com.gunishjain.newsapp.data.model.NewsSourceResponse
import com.gunishjain.newsapp.data.model.Source
import com.gunishjain.newsapp.utils.AppConstant.COUNTRY
import com.gunishjain.newsapp.utils.AppConstant.LANGUAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService

    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        newsRepository = NewsRepository(networkService)
    }

    @Test
    fun getTopHeadlines_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val source = Source(id = "sourceId", name = "sourceName")
            val article = Article(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                source = source
            )

            val articles = mutableListOf<Article>()
            articles.add(article)

            val topHeadlinesResponse = NewsResponse(
                status = "ok", totalResults = 1, articles = articles
            )

            doReturn(topHeadlinesResponse).`when`(networkService).getTopHeadlines(COUNTRY)

            newsRepository.getTopHeadlines(COUNTRY).test {
                assertEquals(topHeadlinesResponse.articles, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(networkService, times(1)).getTopHeadlines(COUNTRY)
        }
    }

    @Test
    fun getTopHeadlines_whenNetworkServiceResponseError_shouldReturnError() {
        runTest {
            val errorMessage = "Error Message For You"

            doThrow(RuntimeException(errorMessage)).`when`(networkService)
                .getTopHeadlines(COUNTRY)

            newsRepository.getTopHeadlines(COUNTRY).test {
                assertEquals(errorMessage, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }
            verify(networkService, times(1)).getTopHeadlines(COUNTRY)
        }
    }


    @Test
    fun getNewsEverything_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val source = Source(id = "sourceId", name = "sourceName")
            val article = Article(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                source = source
            )

            val articles = mutableListOf<Article>()
            articles.add(article)

            val newsEverythingResponse = NewsResponse(
                status = "ok", totalResults = 1, articles = articles
            )

            doReturn(newsEverythingResponse).`when`(networkService).getNewsEverything("sourceId")

            newsRepository.getNewsEverything("sourceId").test {
                assertEquals(newsEverythingResponse.articles, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(networkService, times(1)).getNewsEverything("sourceId")
        }
    }

    @Test
    fun getNewsEverything_whenNetworkServiceResponseError_shouldReturnError() {
        runTest {
            val errorMessage = "Error Message For You"

            doThrow(RuntimeException(errorMessage)).`when`(networkService)
                .getNewsEverything("sourceId")

            newsRepository.getNewsEverything("sourceId").test {
                assertEquals(errorMessage, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }
            verify(networkService, times(1)).getNewsEverything("sourceId")
        }
    }


    @Test
    fun getNewsSources_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val source = Source(id = "sourceId", name = "sourceName")

            val sources = mutableListOf<Source>()
            sources.add(source)

            val newsSourceResponse = NewsSourceResponse(
                status = "ok", sources = sources
            )

            doReturn(newsSourceResponse).`when`(networkService).getNewsSources()

            newsRepository.getNewsSources().test {
                assertEquals(newsSourceResponse.sources, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(networkService, times(1)).getNewsSources()
        }
    }

    @Test
    fun getNewsSources_whenNetworkServiceResponseError_shouldReturnError() {
        runTest {
            val errorMessage = "Error Message For You"

            doThrow(RuntimeException(errorMessage)).`when`(networkService)
                .getNewsSources()

            newsRepository.getNewsSources().test {
                assertEquals(errorMessage, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }
            verify(networkService, times(1)).getNewsSources()
        }
    }

    @Test
    fun getNewsByCountry_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val source = Source(id = "sourceId", name = "sourceName")
            val article = Article(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                source = source
            )

            val articles = mutableListOf<Article>()
            articles.add(article)

            val newsCountryResponse = NewsResponse(
                status = "ok", totalResults = 1, articles = articles
            )

            doReturn(newsCountryResponse).`when`(networkService).getNewsCountry(COUNTRY)

            newsRepository.getNewsByCountry(COUNTRY).test {
                assertEquals(newsCountryResponse.articles, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(networkService, times(1)).getNewsCountry(COUNTRY)
        }
    }

    @Test
    fun getNewsByCountry_whenNetworkServiceResponseError_shouldReturnError() {
        runTest {
            val errorMessage = "Error Message For You"

            doThrow(RuntimeException(errorMessage)).`when`(networkService)
                .getNewsCountry(COUNTRY)

            newsRepository.getNewsByCountry(COUNTRY).test {
                assertEquals(errorMessage, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }
            verify(networkService, times(1)).getNewsCountry(COUNTRY)
        }
    }

    @Test
    fun getNewsByLanguage_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val source = Source(id = "sourceId", name = "sourceName")
            val article = Article(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                source = source
            )

            val articles = mutableListOf<Article>()
            articles.add(article)

            val newsLanguageResponse = NewsResponse(
                status = "ok", totalResults = 1, articles = articles
            )

            doReturn(newsLanguageResponse).`when`(networkService).getNewsLanguage(LANGUAGE)

            newsRepository.getNewsByLanguage(LANGUAGE).test {
                assertEquals(newsLanguageResponse.articles, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(networkService, times(1)).getNewsLanguage(LANGUAGE)
        }
    }

    @Test
    fun getNewsByLanguage_whenNetworkServiceResponseError_shouldReturnError() {
        runTest {
            val errorMessage = "Error Message For You"

            doThrow(RuntimeException(errorMessage)).`when`(networkService)
                .getNewsLanguage(LANGUAGE)

            newsRepository.getNewsByLanguage(LANGUAGE).test {
                assertEquals(errorMessage, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }
            verify(networkService, times(1)).getNewsLanguage(LANGUAGE)
        }
    }


    @Test
    fun getSearchResult_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val source = Source(id = "sourceId", name = "sourceName")
            val article = Article(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                source = source
            )

            val articles = mutableListOf<Article>()
            articles.add(article)

            val searchResponse = NewsResponse(
                status = "ok", totalResults = 1, articles = articles
            )

            doReturn(searchResponse).`when`(networkService).getSearchResult("query")

            newsRepository.getSearchResult("query").test {
                assertEquals(searchResponse.articles, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(networkService, times(1)).getSearchResult("query")
        }
    }

    @Test
    fun getSearchResult_whenNetworkServiceResponseError_shouldReturnError() {
        runTest {
            val errorMessage = "Error Message For You"

            doThrow(RuntimeException(errorMessage)).`when`(networkService)
                .getSearchResult("query")

            newsRepository.getSearchResult("query").test {
                assertEquals(errorMessage, awaitError().message)
                cancelAndIgnoreRemainingEvents()
            }
            verify(networkService, times(1)).getSearchResult("query")
        }
    }


}