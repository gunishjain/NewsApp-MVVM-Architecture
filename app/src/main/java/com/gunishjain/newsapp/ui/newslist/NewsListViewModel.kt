package com.gunishjain.newsapp.ui.newslist

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
class NewsListViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow<PagingData<Article>>(value = PagingData.empty())
    val uiState: StateFlow<PagingData<Article>> = _uiState

    fun fetchNewsBySrc(sourceId: String) {
        viewModelScope.launch {
            newsRepository.getNewsEverything(sourceId)
                .collect {
                    _uiState.value = it
                }
        }
    }

    fun fetchNewsByCountry(countryId: List<String>) {
        viewModelScope.launch {
            if (countryId.size == 1) {
                newsRepository.getNewsByCountry(countryId[0],null)
                    .collect {
                        _uiState.value = it
                    }
            } else {
                val countryIdOne = countryId[0]
                val countryIdTwo = countryId[1]

                newsRepository.getNewsByCountry(countryIdOne,countryIdTwo)
                    .collect {
                        _uiState.value= it
                    }
            }
        }
    }

    fun fetchNewsByLanguage(languageId: List<String>) {
        viewModelScope.launch {
            if (languageId.size == 1) {
                newsRepository.getNewsByLanguage(languageId[0],null)
                    .collect {
                        _uiState.value = it
                    }
            } else {
                val languageIdOne = languageId[0]
                val languageIdTwo = languageId[1]

                newsRepository.getNewsByLanguage(languageIdOne,languageIdTwo)
                    .collect {
                        _uiState.value= it
                    }
            }
        }
    }

}


