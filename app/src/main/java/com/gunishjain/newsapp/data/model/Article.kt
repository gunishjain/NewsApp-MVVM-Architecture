package com.gunishjain.newsapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gunishjain.newsapp.utils.AppConstant.ARTICLE_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = ARTICLE_TABLE)
@Serializable
data class Article(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "article_id")
    val id: Int,
    val title: String = "",
    val description: String? = null,
    val url: String = "",
    val urlToImage: String? = null,
    @Embedded val source: Source
)
