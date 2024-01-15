package com.gunishjain.newsapp.ui.sources

import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.ApiSource
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSourceViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val dispatcherProvider: DispatcherProvider
) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ApiSource>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ApiSource>>> = _uiState

    init {
        fetchSources()
    }

    private fun fetchSources() {
        viewModelScope.launch(dispatcherProvider.main) {
            _uiState.value = UiState.Loading
            newsRepository.getNewsSources()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}