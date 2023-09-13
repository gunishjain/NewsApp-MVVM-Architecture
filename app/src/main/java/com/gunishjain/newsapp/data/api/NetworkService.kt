package com.gunishjain.newsapp.data.api

import com.gunishjain.newsapp.data.model.NewsSourceResponse
import com.gunishjain.newsapp.data.model.TopHeadlinesResponse
import com.gunishjain.newsapp.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkService {

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String) : TopHeadlinesResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines/sources")
    suspend fun getNewsSources(): NewsSourceResponse
}