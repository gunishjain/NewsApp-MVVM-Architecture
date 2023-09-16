package com.gunishjain.newsapp.data.api

import com.gunishjain.newsapp.data.model.NewsSourceResponse
import com.gunishjain.newsapp.data.model.NewsResponse
import com.gunishjain.newsapp.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkService {

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String) : NewsResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines/sources")
    suspend fun getNewsSources(): NewsSourceResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("everything")
    suspend fun getNewsEverything(@Query("sources") sourceId: String) : NewsResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getNewsCountry(@Query("country") countryId: String) : NewsResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getNewsLanguage(@Query("language") languageId: String) : NewsResponse
}