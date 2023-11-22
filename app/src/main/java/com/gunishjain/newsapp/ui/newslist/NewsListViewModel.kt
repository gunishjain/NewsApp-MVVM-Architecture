package com.gunishjain.newsapp.ui.newslist

import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import com.gunishjain.newsapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    fun fetchNewsBySrc(sourceId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            newsRepository.getNewsEverything(sourceId)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun fetchNewsByCountry(countryId: List<String>) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            if (countryId.size == 1) {
                newsRepository.getNewsByCountry(countryId[0])
                    .catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                    }.collect {
                        _uiState.value = UiState.Success(it)
                    }

            } else {
                val countryIdOne = countryId[0]
                val countryIdTwo = countryId[1]

                newsRepository.getNewsByCountry(countryIdOne)
                    .zip(newsRepository.getNewsByCountry(countryIdTwo)) { articlesOne, articlesTwo ->
                        val allArticlesFromApi = mutableListOf<Article>()
                        allArticlesFromApi.addAll(articlesOne)
                        allArticlesFromApi.addAll(articlesTwo)

                        return@zip allArticlesFromApi.shuffled()
                    }.catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                    }.collect {
                        _uiState.value = UiState.Success(it)
                    }
            }
        }
    }

    fun fetchNewsByLanguage(languageId: List<String>) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            if (languageId.size == 1) {
                newsRepository.getNewsByLanguage(languageId[0])
                    .catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                    }.collect {
                        _uiState.value = UiState.Success(it)
                    }
            } else {
                val languageIdOne = languageId[0]
                val languageIdTwo = languageId[1]

                newsRepository.getNewsByLanguage(languageIdOne)
                    .zip(newsRepository.getNewsByLanguage(languageIdTwo)) { articlesOne, articlesTwo ->

                        val allArticlesFromApi = mutableListOf<Article>()
                        allArticlesFromApi.addAll(articlesOne)
                        allArticlesFromApi.addAll(articlesTwo)

                        return@zip allArticlesFromApi.shuffled()
                    }.catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                    }.collect {
                        _uiState.value = UiState.Success(it)
                    }
            }
        }
    }

}


