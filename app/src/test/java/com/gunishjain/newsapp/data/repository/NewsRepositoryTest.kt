package com.gunishjain.newsapp.data.repository

import app.cash.turbine.test
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.model.ApiArticle
import com.gunishjain.newsapp.data.model.NewsResponse
import com.gunishjain.newsapp.data.model.NewsSourceResponse
import com.gunishjain.newsapp.data.model.ApiSource
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
            val apiSource = ApiSource(id = "sourceId", name = "sourceName")
            val apiArticle = ApiArticle(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                apiSource = apiSource
            )

            val apiArticles = mutableListOf<ApiArticle>()
            apiArticles.add(apiArticle)

            val topHeadlinesResponse = NewsResponse(
                status = "ok", totalResults = 1, apiArticles = apiArticles
            )

            doReturn(topHeadlinesResponse).`when`(networkService).getTopHeadlines(COUNTRY)

            newsRepository.getTopHeadlines(COUNTRY).test {
                assertEquals(topHeadlinesResponse.apiArticles, awaitItem())
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
            val apiSource = ApiSource(id = "sourceId", name = "sourceName")
            val apiArticle = ApiArticle(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                apiSource = apiSource
            )

            val apiArticles = mutableListOf<ApiArticle>()
            apiArticles.add(apiArticle)

            val newsEverythingResponse = NewsResponse(
                status = "ok", totalResults = 1, apiArticles = apiArticles
            )

            doReturn(newsEverythingResponse).`when`(networkService).getNewsEverything("sourceId")

            newsRepository.getNewsEverything("sourceId").test {
                assertEquals(newsEverythingResponse.apiArticles, awaitItem())
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
            val apiSource = ApiSource(id = "sourceId", name = "sourceName")

            val apiSources = mutableListOf<ApiSource>()
            apiSources.add(apiSource)

            val newsSourceResponse = NewsSourceResponse(
                status = "ok", apiSources = apiSources
            )

            doReturn(newsSourceResponse).`when`(networkService).getNewsSources()

            newsRepository.getNewsSources().test {
                assertEquals(newsSourceResponse.apiSources, awaitItem())
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
            val apiSource = ApiSource(id = "sourceId", name = "sourceName")
            val apiArticle = ApiArticle(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                apiSource = apiSource
            )

            val apiArticles = mutableListOf<ApiArticle>()
            apiArticles.add(apiArticle)

            val newsCountryResponse = NewsResponse(
                status = "ok", totalResults = 1, apiArticles = apiArticles
            )

            doReturn(newsCountryResponse).`when`(networkService).getNewsCountry(COUNTRY)

            newsRepository.getNewsByCountry(COUNTRY).test {
                assertEquals(newsCountryResponse.apiArticles, awaitItem())
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
            val apiSource = ApiSource(id = "sourceId", name = "sourceName")
            val apiArticle = ApiArticle(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                apiSource = apiSource
            )

            val apiArticles = mutableListOf<ApiArticle>()
            apiArticles.add(apiArticle)

            val newsLanguageResponse = NewsResponse(
                status = "ok", totalResults = 1, apiArticles = apiArticles
            )

            doReturn(newsLanguageResponse).`when`(networkService).getNewsLanguage(LANGUAGE)

            newsRepository.getNewsByLanguage(LANGUAGE).test {
                assertEquals(newsLanguageResponse.apiArticles, awaitItem())
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
            val apiSource = ApiSource(id = "sourceId", name = "sourceName")
            val apiArticle = ApiArticle(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                apiSource = apiSource
            )

            val apiArticles = mutableListOf<ApiArticle>()
            apiArticles.add(apiArticle)

            val searchResponse = NewsResponse(
                status = "ok", totalResults = 1, apiArticles = apiArticles
            )

            doReturn(searchResponse).`when`(networkService).getSearchResult("query")

            newsRepository.getSearchResult("query").test {
                assertEquals(searchResponse.apiArticles, awaitItem())
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