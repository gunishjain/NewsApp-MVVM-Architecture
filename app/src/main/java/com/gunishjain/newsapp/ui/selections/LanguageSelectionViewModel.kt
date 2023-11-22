package com.gunishjain.newsapp.ui.selections

import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.Language
import com.gunishjain.newsapp.data.repository.NewsLocalRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import com.gunishjain.newsapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageSelectionViewModel @Inject constructor(private val newsLocalRepository: NewsLocalRepository) :
    BaseViewModel() {

    private val _uiStateLanguage = MutableStateFlow<UiState<List<Language>>>(UiState.Loading)
    val uiStateLanguage: StateFlow<UiState<List<Language>>> = _uiStateLanguage

    init {
        getLanguages()
    }

    private fun getLanguages() {
        viewModelScope.launch {
            newsLocalRepository.getLanguages()
                .catch { e ->
                    _uiStateLanguage.value = UiState.Error(e.toString())
                }.collect {
                    _uiStateLanguage.value = UiState.Success(it)
                }
        }
    }

}