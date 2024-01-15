package com.gunishjain.newsapp.ui.topheadlines

import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.local.entity.Article
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.utils.AppConstant.COUNTRY
import com.gunishjain.newsapp.utils.DispatcherProvider
import com.gunishjain.newsapp.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val dispatcherProvider: DispatcherProvider,
    networkHelper: NetworkHelper
) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    init {

        if (networkHelper.isNetworkConnected()) {
            fetchNews()
        } else {
            fetchNewsDirectlyFromDB()
        }
    }

    fun fetchNews() {
        viewModelScope.launch(dispatcherProvider.main) {
            _uiState.value = UiState.Loading
            newsRepository.getTopHeadlines(COUNTRY)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    private fun fetchNewsDirectlyFromDB() {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getArticlesDirectlyFromDB()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}