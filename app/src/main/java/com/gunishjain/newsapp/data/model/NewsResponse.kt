package com.gunishjain.newsapp.data.model

data class NewsResponse(

    val status: String = "",
    val totalResults : Int=0,
    val articles: List<Article> = ArrayList()
)
