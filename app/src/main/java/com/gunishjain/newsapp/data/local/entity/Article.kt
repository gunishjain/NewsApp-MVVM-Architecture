package com.gunishjain.newsapp.data.local.entity

import com.gunishjain.newsapp.data.model.ApiArticle
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "article_id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "url")
    val url: String = "",
    @ColumnInfo(name = "urlToImage")
    val imageUrl: String? = "",
    @Embedded val source: Source
)

fun Article.toArticleModel(): ApiArticle {
    return ApiArticle(
        title = title,
        description = description.let { it.toString() },
        url = url,
        imageUrl = imageUrl.let { it.toString() },
        apiSource = source.toSourceModel()
    )

}
