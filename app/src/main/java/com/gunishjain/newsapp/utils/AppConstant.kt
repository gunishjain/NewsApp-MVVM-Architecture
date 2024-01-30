package com.gunishjain.newsapp.utils

import com.gunishjain.newsapp.BuildConfig

object AppConstant {
    const val APP_NAME = "NewsApp"
    const val API_KEY = BuildConfig.API_KEY
    const val COUNTRY = "in"
    const val LANGUAGE = "en"
    const val BASE_URL = "https://newsapi.org/v2/"
    const val DEBOUNCE_TIMEOUT = 300L
    const val MIN_SEARCH_CHAR = 3
    const val WORK_NAME="newsWork"
}