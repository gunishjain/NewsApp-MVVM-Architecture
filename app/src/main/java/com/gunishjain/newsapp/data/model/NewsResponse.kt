package com.gunishjain.newsapp.data.model

import ApiArticle
import com.google.gson.annotations.SerializedName

data class NewsResponse(

    val status: String = "",
    val totalResults: Int = 0,
    @SerializedName("articles")
    val apiArticles: List<ApiArticle> = ArrayList()
)
