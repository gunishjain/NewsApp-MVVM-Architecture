package com.gunishjain.newsapp.data.model

data class Country(
    val id : String? = null,
    val name: String ="",
)

data class SelectionState(
    val selectedCountries: List<Country> = emptyList()
)
