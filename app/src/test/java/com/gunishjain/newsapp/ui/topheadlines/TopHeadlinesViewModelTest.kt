package com.gunishjain.newsapp.ui.topheadlines

import app.cash.turbine.test
import com.gunishjain.newsapp.data.model.ApiArticle
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.utils.AppConstant.COUNTRY
import com.gunishjain.newsapp.utils.DispatcherProvider
import com.gunishjain.newsapp.utils.NetworkHelper
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
class TopHeadlinesViewModelTest {

    @Mock
    private lateinit var newsRepository: NewsRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun fetchNews_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            doReturn(flowOf(emptyList<ApiArticle>()))
                .`when`(newsRepository)
                .getTopHeadlines(COUNTRY)
            val viewModel = TopHeadlinesViewModel(newsRepository, dispatcherProvider,networkHelper)
            viewModel.uiState.test {
                assertEquals(UiState.Success(emptyList<List<ApiArticle>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getTopHeadlines(COUNTRY)
        }
    }

    @Test
    fun fetchNews_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            val errorMessage = "Error Message For You"
            doReturn(flow<List<ApiArticle>> {
                throw IllegalStateException(errorMessage)
            })
                .`when`(newsRepository)
                .getTopHeadlines(COUNTRY)

            val viewModel = TopHeadlinesViewModel(newsRepository, dispatcherProvider,networkHelper)
            viewModel.uiState.test {
                assertEquals(
                    UiState.Error(IllegalStateException(errorMessage).toString()),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getTopHeadlines(COUNTRY)
        }
    }

}