package com.gunishjain.newsapp.ui.search

import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.utils.AppConstant.DEBOUNCE_TIMEOUT
import com.gunishjain.newsapp.utils.AppConstant.MIN_SEARCH_CHAR
import com.gunishjain.newsapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val dispatcherProvider: DispatcherProvider
) :
    BaseViewModel() {

    private val searchText = MutableStateFlow("")
    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    init {
        createNewsFlow()
    }

    private fun createNewsFlow() {
        viewModelScope.launch(dispatcherProvider.main) {
            searchText.debounce(DEBOUNCE_TIMEOUT)
                .filter {
                    if (it.isNotEmpty() && it.length >= MIN_SEARCH_CHAR) {
                        return@filter true
                    } else {
                        _uiState.value = UiState.Success(emptyList())
                        return@filter false
                    }
                }.distinctUntilChanged()
                .flatMapLatest {
                    _uiState.value = UiState.Loading
                    return@flatMapLatest newsRepository.getSearchResult(it)
                        .catch { e ->
                            _uiState.value = UiState.Error(e.toString())
                        }
                }
                .flowOn(dispatcherProvider.io)
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun onQuerySearch(query: String) {
        searchText.value = query
    }

}

