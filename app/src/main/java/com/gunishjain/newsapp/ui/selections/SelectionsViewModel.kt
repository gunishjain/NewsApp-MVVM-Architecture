package com.gunishjain.newsapp.ui.selections

import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.Country
import com.gunishjain.newsapp.data.model.Language
import com.gunishjain.newsapp.data.repository.NewsLocalRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import com.gunishjain.newsapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SelectionsViewModel(private val newsLocalRepository: NewsLocalRepository): BaseViewModel() {

    private val _uiStateCountry = MutableStateFlow<UiState<List<Country>>>(UiState.Loading)

    private val _uiStateLanguage= MutableStateFlow<UiState<List<Language>>>(UiState.Loading)

    val uiStateCountry : StateFlow<UiState<List<Country>>> = _uiStateCountry
    val uiStateLanguage : StateFlow<UiState<List<Language>>> = _uiStateLanguage

    init {
        getCountries()
        getLanguages()
    }


    private fun getCountries() {
        viewModelScope.launch {
            newsLocalRepository.getCountries()
                .catch {e->
                    _uiStateCountry.value=UiState.Error(e.toString())
                }.collect {
                    _uiStateCountry.value= UiState.Success(it)
                }
        }
    }

    private fun getLanguages() {
        viewModelScope.launch {
            newsLocalRepository.getLanguages()
                .catch {e->
                    _uiStateLanguage.value=UiState.Error(e.toString())
                }.collect {
                    _uiStateLanguage.value= UiState.Success(it)
                }
        }
    }
}