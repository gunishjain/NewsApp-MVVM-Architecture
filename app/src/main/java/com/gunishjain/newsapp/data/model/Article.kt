package com.gunishjain.newsapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gunishjain.newsapp.utils.AppConstant.ARTICLE_TABLE
import kotlinx.serialization.Serializable

@Entity(tableName = ARTICLE_TABLE)
@Serializable
data class Article(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "article_id")
    val id: String,
    val title: String = "",
    val description: String? = null,
    val url: String = "",
    val urlToImage: String? = null,
    @Embedded val source: Source
)
