package com.gunishjain.newsapp.data.api

import com.gunishjain.newsapp.di.NetworkApiKey
import com.gunishjain.newsapp.utils.AppConstant.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(@NetworkApiKey private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("X-Api-Key", API_KEY)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}