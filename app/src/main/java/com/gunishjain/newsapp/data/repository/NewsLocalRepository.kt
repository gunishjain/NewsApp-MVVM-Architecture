package com.gunishjain.newsapp.data.repository

import com.gunishjain.newsapp.data.model.Country
import com.gunishjain.newsapp.data.model.Language
import com.gunishjain.newsapp.utils.countries
import com.gunishjain.newsapp.utils.languages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsLocalRepository @Inject constructor() {

    fun getCountries(): Flow<List<Country>> {
        return flow {
            emit(countries)
        }
    }

    fun getLanguages(): Flow<List<Language>> {
        return flow {
            emit(languages)
        }
    }

}