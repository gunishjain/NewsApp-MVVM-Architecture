package com.gunishjain.newsapp.data.model

import com.google.gson.annotations.SerializedName
import com.gunishjain.newsapp.data.local.entity.Article

data class ApiArticle(
    val title: String = "",
    val description: String = "",
    val url: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String = "",
    @SerializedName("source")
    val apiSource: ApiSource
)


fun ApiArticle.toArticleEntity(): Article {
    return Article(
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        source = apiSource.toSourceEntity()
    )
}