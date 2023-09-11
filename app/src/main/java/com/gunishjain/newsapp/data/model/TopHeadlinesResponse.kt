package com.gunishjain.newsapp.data.model

data class TopHeadlinesResponse(

    val status: String = "",
    val totalResults : Int=0,
    val articles: List<Article> = ArrayList()
)
