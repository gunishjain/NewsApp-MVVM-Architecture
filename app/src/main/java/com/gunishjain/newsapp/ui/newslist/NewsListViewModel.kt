package com.gunishjain.newsapp.ui.newslist

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow<PagingData<Article>>(value = PagingData.empty())
    val uiState: StateFlow<PagingData<Article>> = _uiState

    fun fetchNewsOnSrc(sourceId: String) {
        viewModelScope.launch {
            newsRepository.getNewsEverything(sourceId)
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun fetchNewsOnCountry(countryId: List<String>) {
        viewModelScope.launch {
            if (countryId.size == 1) {
                newsRepository.getNewsCountry(countryId[0],null)
                    .collect {
                        _uiState.value = it
                    }
            } else {
                val countryIdOne = countryId[0]
                val countryIdTwo = countryId[1]

                newsRepository.getNewsCountry(countryIdOne,countryIdTwo)
                    .collect {
                        _uiState.value= it
                    }
            }
        }
    }

    fun fetchNewsOnLanguage(languageId: List<String>) {
        viewModelScope.launch {
            if (languageId.size == 1) {
                newsRepository.getNewsLanguage(languageId[0],null)
                    .collect {
                        _uiState.value = it
                    }
            } else {
                val languageIdOne = languageId[0]
                val languageIdTwo = languageId[1]

                newsRepository.getNewsLanguage(languageIdOne,languageIdTwo)
                    .collect {
                        _uiState.value= it
                    }
            }
        }
    }

}


