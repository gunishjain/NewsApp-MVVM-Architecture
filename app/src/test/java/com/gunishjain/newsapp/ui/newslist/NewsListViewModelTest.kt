package com.gunishjain.newsapp.ui.newslist

import app.cash.turbine.test
import com.gunishjain.newsapp.data.model.ApiArticle
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.utils.AppConstant.COUNTRY
import com.gunishjain.newsapp.utils.DispatcherProvider
import com.gunishjain.newsapp.utils.TestDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
class NewsListViewModelTest {

    @Mock
    private lateinit var newsRepository: NewsRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun fetchNewsBySrc_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            doReturn(flowOf(emptyList<ApiArticle>()))
                .`when`(newsRepository)
                .getNewsEverything("en")
            val viewModel = NewsListViewModel(newsRepository, dispatcherProvider)
            viewModel.fetchNewsBySrc("en")
            viewModel.uiState.test {
                assertEquals(UiState.Success(emptyList<List<ApiArticle>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getNewsEverything("en")
        }
    }

    @Test
    fun fetchNewsBySrc_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            val errorMessage = "Error Message For You"
            doReturn(flow<List<ApiArticle>> {
                throw IllegalStateException(errorMessage)
            })
                .`when`(newsRepository)
                .getNewsEverything("en")

            val viewModel = NewsListViewModel(newsRepository, dispatcherProvider)
            viewModel.fetchNewsBySrc("en")
            viewModel.uiState.test {
                assertEquals(
                    UiState.Error(IllegalStateException(errorMessage).toString()),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getNewsEverything("en")
        }
    }

    @Test
    fun fetchNewsByLang_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            doReturn(flowOf(emptyList<ApiArticle>()))
                .`when`(newsRepository)
                .getNewsByLanguage("en")
            val viewModel = NewsListViewModel(newsRepository, dispatcherProvider)
            viewModel.fetchNewsByLanguage(listOf("en"))
            viewModel.uiState.test {
                assertEquals(UiState.Success(emptyList<List<ApiArticle>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getNewsByLanguage("en")
        }
    }

    @Test
    fun fetchNewsByLang_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            val errorMessage = "Error Message For You"
            doReturn(flow<List<ApiArticle>> {
                throw IllegalStateException(errorMessage)
            })
                .`when`(newsRepository)
                .getNewsByLanguage("en")

            val viewModel = NewsListViewModel(newsRepository, dispatcherProvider)
            viewModel.fetchNewsByLanguage(listOf("en"))
            viewModel.uiState.test {
                assertEquals(
                    UiState.Error(IllegalStateException(errorMessage).toString()),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getNewsByLanguage("en")
        }
    }

    @Test
    fun fetchNewsByCountry_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            doReturn(flowOf(emptyList<ApiArticle>()))
                .`when`(newsRepository)
                .getNewsByCountry(COUNTRY)
            val viewModel = NewsListViewModel(newsRepository, dispatcherProvider)
            viewModel.fetchNewsByCountry(listOf(COUNTRY))
            viewModel.uiState.test {
                assertEquals(UiState.Success(emptyList<List<ApiArticle>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getNewsByCountry(COUNTRY)
        }
    }

    @Test
    fun fetchNewsByCountry_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            val errorMessage = "Error Message For You"
            doReturn(flow<List<ApiArticle>> {
                throw IllegalStateException(errorMessage)
            })
                .`when`(newsRepository)
                .getNewsByCountry(COUNTRY)

            val viewModel = NewsListViewModel(newsRepository, dispatcherProvider)
            viewModel.fetchNewsByCountry(listOf(COUNTRY))
            viewModel.uiState.test {
                assertEquals(
                    UiState.Error(IllegalStateException(errorMessage).toString()),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getNewsByCountry(COUNTRY)
        }
    }


}