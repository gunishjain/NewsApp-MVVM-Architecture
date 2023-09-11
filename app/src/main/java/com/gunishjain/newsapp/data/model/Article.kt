package com.gunishjain.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    val title:String = "",
    val description : String = "",
    val url : String = "",
    @SerializedName("urlToImage")
    val imageUrl : String = "",
    val source: Source
)
