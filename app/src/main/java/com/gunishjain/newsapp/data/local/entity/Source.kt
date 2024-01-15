package com.gunishjain.newsapp.data.local.entity

import androidx.room.ColumnInfo
import com.gunishjain.newsapp.data.model.ApiSource

data class Source(
    @ColumnInfo(name = "sourceId")
    val id: String?,
    @ColumnInfo(name = "sourceName")
    val name: String = ""
)


fun Source.toSourceModel() : ApiSource {
    return ApiSource(id,name)
}