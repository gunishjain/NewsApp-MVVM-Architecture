package com.gunishjain.newsapp.data.repository

import app.cash.turbine.test
import com.gunishjain.newsapp.utils.countries
import com.gunishjain.newsapp.utils.languages
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsLocalRepositoryTest {


    private lateinit var newsLocalRepository: NewsLocalRepository

    @Before
    fun setUp() {
        newsLocalRepository = NewsLocalRepository()
    }

    @Test
    fun getCountries_with_ValidData() {
        runTest {
            val countryList = countries
            newsLocalRepository.getCountries().test {
                assertEquals(countryList, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun getLanguages_with_ValidData() {
        runTest {
            val languageList = languages
            newsLocalRepository.getLanguages().test {
                assertEquals(languageList, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    //TODO: Need to write test when the local data is empty.

}
