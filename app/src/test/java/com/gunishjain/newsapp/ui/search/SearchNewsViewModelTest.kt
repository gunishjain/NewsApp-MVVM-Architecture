package com.gunishjain.newsapp.ui.search

import app.cash.turbine.test
import com.gunishjain.newsapp.data.model.Article
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
class SearchNewsViewModelTest {

    @Mock
    private lateinit var newsRepository: NewsRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun fetchSearchResult_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {

            doReturn(flowOf(emptyList<Article>()))
                .`when`(newsRepository)
                .getSearchResult("query")
            val viewModel = SearchNewsViewModel(newsRepository, dispatcherProvider)
            viewModel.uiState.test {
                assertEquals(UiState.Success(emptyList<List<Article>>()), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getSearchResult("query")
        }
    }

    @Test
    fun fetchSearchResult_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            val errorMessage = "Error Message For You"
            doReturn(flow<List<Article>> {
                throw IllegalStateException(errorMessage)
            })
                .`when`(newsRepository)
                .getSearchResult("query")

            val viewModel = SearchNewsViewModel(newsRepository, dispatcherProvider)
            viewModel.uiState.test {
                assertEquals(
                    UiState.Error(IllegalStateException(errorMessage).toString()),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            verify(newsRepository, times(1)).getSearchResult("query")
        }
    }
}