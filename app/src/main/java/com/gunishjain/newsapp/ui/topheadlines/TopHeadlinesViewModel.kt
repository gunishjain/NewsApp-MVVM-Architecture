package com.gunishjain.newsapp.ui.topheadlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.repository.TopHeadlinesRepository
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.utils.AppConstant.COUNTRY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TopHeadlinesViewModel (private val topHeadlinesRepository: TopHeadlinesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState : StateFlow<UiState<List<Article>>> = _uiState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            topHeadlinesRepository.getTopHeadlines(COUNTRY)
                .catch { e->
                    _uiState.value=UiState.Error(e.toString())
                }.collect {
                    _uiState.value= UiState.Success(it)
                }
        }
    }
}