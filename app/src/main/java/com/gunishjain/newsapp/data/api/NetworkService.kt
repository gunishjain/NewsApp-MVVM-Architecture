package com.gunishjain.newsapp.data.api

import com.gunishjain.newsapp.data.model.NewsResponse
import com.gunishjain.newsapp.data.model.NewsSourceResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String,
                                @Query("page") page: Int,
                                @Query("pageSize") pageSize: Int): NewsResponse

    @GET("top-headlines/sources")
    suspend fun getNewsSources(): NewsSourceResponse

    @GET("everything")
    suspend fun getNewsEverything(@Query("sources") sourceId: String,
                                  @Query("page") page: Int,
                                  @Query("pageSize") pageSize: Int): NewsResponse

    @GET("top-headlines")
    suspend fun getNewsCountry(@Query("country") countryId: String,
                               @Query("page") page: Int,
                               @Query("pageSize") pageSize: Int): NewsResponse

    @GET("top-headlines")
    suspend fun getNewsLanguage(@Query("language") languageId: String,
                                @Query("page") page: Int,
                                @Query("pageSize") pageSize: Int): NewsResponse

    @GET("everything")
    suspend fun getSearchResult(@Query("q") query: String): NewsResponse
}