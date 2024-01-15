package com.gunishjain.newsapp.data.model

import com.gunishjain.newsapp.data.local.entity.Source

data class ApiSource(
    val id: String?,
    val name: String = ""
)

fun ApiSource.toSourceEntity(): Source {
    return Source(id, name)
}