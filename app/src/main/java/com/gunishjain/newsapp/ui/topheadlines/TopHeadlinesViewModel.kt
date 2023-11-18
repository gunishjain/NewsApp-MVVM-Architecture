package com.gunishjain.newsapp.ui.topheadlines

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow<PagingData<Article>>(value = PagingData.empty())
    val uiState: StateFlow<PagingData<Article>> = _uiState

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            newsRepository.getTopHeadlines()
                .collect {
                    _uiState.value = it
                }
        }
    }


}