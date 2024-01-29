package com.gunishjain.newsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gunishjain.newsapp.utils.AppConstant.ARTICLE_REMOTE_KEYS_TABLE

@Entity
data class ArticleRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
