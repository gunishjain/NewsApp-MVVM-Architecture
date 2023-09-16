package com.gunishjain.newsapp.ui.newslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewsListViewModel(private val newsRepository: NewsRepository) : ViewModel(){

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<Article>>> = _uiState

     fun fetchNewsOnSrc(sourceId: String){
        viewModelScope.launch {
            newsRepository.getNewsEverything(sourceId)
                .catch {e->
                    _uiState.value=UiState.Error(e.toString())
                }.collect {
                    _uiState.value= UiState.Success(it)
                }
        }
    }

    fun fetchNewsOnCountry(countryId: String){
        viewModelScope.launch {
            newsRepository.getNewsCountry(countryId)
                .catch {e->
                    _uiState.value=UiState.Error(e.toString())
                }.collect {
                    _uiState.value= UiState.Success(it)
                }
        }
    }

    fun fetchNewsOnLanguage(languageId: String){
        viewModelScope.launch {
            newsRepository.getNewsLanguage(languageId)
                .catch {e->
                    _uiState.value=UiState.Error(e.toString())
                }.collect {
                    _uiState.value= UiState.Success(it)
                }
        }
    }


}