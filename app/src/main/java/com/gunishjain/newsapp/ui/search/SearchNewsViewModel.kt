package com.gunishjain.newsapp.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import com.gunishjain.newsapp.utils.AppConstant.DEBOUNCE_TIMEOUT
import com.gunishjain.newsapp.utils.AppConstant.MIN_SEARCH_CHAR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    private val searchText = MutableStateFlow("")
    private val _uiState = MutableStateFlow<PagingData<Article>>(value = PagingData.empty())
    val uiState: StateFlow<PagingData<Article>> = _uiState

    init {
        createNewsFlow()
    }

    private fun createNewsFlow() {
        viewModelScope.launch {
            searchText.debounce(DEBOUNCE_TIMEOUT)
                .filter {
                    if (it.isNotEmpty() && it.length >= MIN_SEARCH_CHAR) {
                        return@filter true
                    } else {
                        _uiState.value = PagingData.empty()
                        return@filter false
                    }
                }.distinctUntilChanged()
                .flatMapLatest {
                    return@flatMapLatest newsRepository.getSearchResult(it)
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun onQuerySearch(query: String) {
        searchText.value = query
    }

}

