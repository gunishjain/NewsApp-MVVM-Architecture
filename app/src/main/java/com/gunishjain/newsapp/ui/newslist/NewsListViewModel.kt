package com.gunishjain.newsapp.ui.newslist

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
        Log.d("nl-vm", countryId.size.toString())
        viewModelScope.launch {
            if (countryId.size == 1) {
                newsRepository.getNewsCountry(countryId[0])
                    .collect {
                        _uiState.value = it
                    }
            } else {
                val countryIdOne = countryId[0]
                val countryIdTwo = countryId[1]
                val article1 = newsRepository.getNewsCountry(countryIdOne)
                val article2 = newsRepository.getNewsCountry(countryIdTwo)

                val combinedData = merge(article1, article2)
//                            val combinedFlow: Flow<PagingData<Article>> = combinedData
//                                .asFlow()
                combinedData.collectLatest {
                    Log.d("collecting", it.toString())
                    _uiState.value = it
                }

            }
        }
    }


//    fun fetchNewsOnLanguage(languageId: List<String>) {
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            if (languageId.size == 1) {
//                newsRepository.getNewsLanguage(languageId[0])
//                    .catch { e ->
//                        _uiState.value = UiState.Error(e.toString())
//                    }.collect {
//                        _uiState.value = UiState.Success(it)
//                    }
//            } else {
//                val languageIdOne = languageId[0]
//                val languageIdTwo = languageId[1]
//
//                newsRepository.getNewsLanguage(languageIdOne)
//                    .zip(newsRepository.getNewsLanguage(languageIdTwo)) { articlesOne, articlesTwo ->
//
//                        val allArticlesFromApi = mutableListOf<Article>()
//                        allArticlesFromApi.addAll(articlesOne)
//                        allArticlesFromApi.addAll(articlesTwo)
//
//                        return@zip allArticlesFromApi.shuffled()
//                    }.catch { e ->
//                        _uiState.value = UiState.Error(e.toString())
//                    }.collect {
//                        _uiState.value = UiState.Success(it)
//                    }
//            }

}


