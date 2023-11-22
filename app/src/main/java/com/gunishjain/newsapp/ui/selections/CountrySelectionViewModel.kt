package com.gunishjain.newsapp.ui.selections

import androidx.lifecycle.viewModelScope
import com.gunishjain.newsapp.data.model.Country
import com.gunishjain.newsapp.data.repository.NewsLocalRepository
import com.gunishjain.newsapp.ui.base.BaseViewModel
import com.gunishjain.newsapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountrySelectionViewModel @Inject constructor(private val newsLocalRepository: NewsLocalRepository) :
    BaseViewModel() {

    private val _uiStateCountry = MutableStateFlow<UiState<List<Country>>>(UiState.Loading)
    val uiStateCountry: StateFlow<UiState<List<Country>>> = _uiStateCountry

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            newsLocalRepository.getCountries()
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    _uiStateCountry.value = UiState.Error(e.toString())
                }.collect {
                    _uiStateCountry.value = UiState.Success(it)
                }
        }
    }

}