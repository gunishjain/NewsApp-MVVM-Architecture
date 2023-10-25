package com.gunishjain.newsapp.ui.newslist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import com.gunishjain.newsapp.ui.base.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsListViewModel(private val newsRepository: NewsRepository) : BaseViewModel(){

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<Article>>> = _uiState

     fun fetchNewsOnSrc(sourceId: String){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            newsRepository.getNewsEverything(sourceId)
                .catch {e->
                    _uiState.value=UiState.Error(e.toString())
                }.collect {
                    _uiState.value= UiState.Success(it)
                }
        }
    }

    fun fetchNewsOnCountry(countryId: List<String>){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            if(countryId.size==1){
                newsRepository.getNewsCountry(countryId[0])
                    .catch {e->
                        _uiState.value=UiState.Error(e.toString())
                    }.collect {
                        _uiState.value= UiState.Success(it)
                    }
            } else {
                val countryIdOne = countryId[0]
                val countryIdTwo = countryId[1]

                newsRepository.getNewsCountry(countryIdOne)
                    .zip(newsRepository.getNewsCountry(countryIdTwo)){ articlesOne,articlesTwo->

                        val allArticlesFromApi = mutableListOf<Article>()
                        allArticlesFromApi.addAll(articlesOne)
                        allArticlesFromApi.addAll(articlesTwo)

                        return@zip allArticlesFromApi.shuffled()
                    }.catch {e->
                        _uiState.value=UiState.Error(e.toString())
                    }.collect {
                        _uiState.value= UiState.Success(it)
                        Log.d("GUNISH",it.size.toString())
                    }
            }

//            newsRepository.getNewsCountry(countryId)
//                .catch {e->
//                    _uiState.value=UiState.Error(e.toString())
//                }.collect {
//                    _uiState.value= UiState.Success(it)
//                }
        }
    }

    fun fetchNewsOnLanguage(languageId: List<String>){
        viewModelScope.launch {
            _uiState.value=UiState.Loading
            if(languageId.size==1){
                newsRepository.getNewsLanguage(languageId[0])
                    .catch {e->
                        _uiState.value=UiState.Error(e.toString())
                    }.collect {
                        _uiState.value= UiState.Success(it)
                    }
            } else {
                val languageIdOne = languageId[0]
                val languageIdTwo = languageId[1]

                newsRepository.getNewsLanguage(languageIdOne)
                    .zip(newsRepository.getNewsLanguage(languageIdTwo)){ articlesOne,articlesTwo->

                        val allArticlesFromApi = mutableListOf<Article>()
                        allArticlesFromApi.addAll(articlesOne)
                        allArticlesFromApi.addAll(articlesTwo)

                        return@zip allArticlesFromApi.shuffled()
                    }.catch {e->
                        _uiState.value=UiState.Error(e.toString())
                    }.collect {
                        _uiState.value= UiState.Success(it)
                    }
            }

//            newsRepository.getNewsLanguage(languageId)
//                .catch {e->
//                    _uiState.value=UiState.Error(e.toString())
//                }.collect {
//                    _uiState.value= UiState.Success(it)
//                }
        }
    }


}