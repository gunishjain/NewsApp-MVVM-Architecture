package com.gunishjain.newsapp.data.api

import com.gunishjain.newsapp.data.model.TopHeadlinesResponse
import com.gunishjain.newsapp.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkService {

    @Headers("X-Api-Key: $API_KEY")
    @GET("")
    suspend fun getTopHeadlines(@Query("country") country: String) : TopHeadlinesResponse
}