package com.gunishjain.newsapp.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchNewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<Article>>> = _uiState

    init {
        _searchText
            .debounce(300)
            .filter { queryPrefix-> (queryPrefix.length>1) }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .onEach {
                fetchQueryResult(it)
            }.launchIn(viewModelScope)
    }

    fun onQuerySearch(query: String) {
        _searchText.value = query
    }

      private fun fetchQueryResult(query: String) {
        viewModelScope.launch {
           newsRepository.getSearchResult(query)
               .catch { e->
                   _uiState.value=UiState.Error(e.toString())
               }.collect {
                   Log.d("SearchNewsViewModel", "Received search results: ${it.size}")
                   _uiState.value= UiState.Success(it)
               }
        }
     }
}

