package com.gunishjain.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class NewsSourceResponse(
    val status: String = "",
    @SerializedName("sources")
    val apiSources : List<ApiSource> = ArrayList()
)
