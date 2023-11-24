package com.gunishjain.newsapp.ui.sources

import app.cash.turbine.test
import com.gunishjain.newsapp.data.model.Source
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.UiState
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
class NewsSourceViewModelTest {

    @Mock
    private lateinit var newsRepository: NewsRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun fetchSources_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            doReturn(flowOf(emptyList<Source>()))
                .`when`(newsRepository)
                .getNewsSources()
            val viewModel = NewsSourceViewModel(newsRepository, dispatcherProvider)
            viewModel.uiState.test {
                assertEquals(UiState.Success(emptyList<List<Source>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getNewsSources()
        }
    }

    @Test
    fun fetchSources_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            val errorMessage = "Error Message For You"
            doReturn(flow<List<Source>> {
                throw IllegalStateException(errorMessage)
            })
                .`when`(newsRepository)
                .getNewsSources()

            val viewModel = NewsSourceViewModel(newsRepository, dispatcherProvider)
            viewModel.uiState.test {
                assertEquals(
                    UiState.Error(IllegalStateException(errorMessage).toString()),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getNewsSources()
        }
    }

}