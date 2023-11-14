package com.gunishjain.newsapp.data.model

data class Language(
    val id: String? = null,
    val name: String = "",
)

data class LanguageState(
    val selectedLanguages: List<Language> = emptyList()
)