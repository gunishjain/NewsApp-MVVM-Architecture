package com.gunishjain.newsapp.ui.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.Source
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsSourceViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)

    val uiState : StateFlow<UiState<List<Source>>> = _uiState

    init {
        fetchSources()
    }

    private fun fetchSources() {
        viewModelScope.launch {
            newsRepository.getNewsSources()
                .catch {e->
                    _uiState.value=UiState.Error(e.toString())
                }.collect {
                    _uiState.value= UiState.Success(it)
                }
        }
    }

}