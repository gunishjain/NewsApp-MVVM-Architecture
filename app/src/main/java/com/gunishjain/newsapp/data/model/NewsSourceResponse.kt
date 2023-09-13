package com.gunishjain.newsapp.data.model

data class NewsSourceResponse(
    val status: String = "",
    val sources : List<Source> = ArrayList()
)
