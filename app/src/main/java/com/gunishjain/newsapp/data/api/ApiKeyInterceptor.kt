package com.gunishjain.newsapp.data.api

import com.gunishjain.newsapp.di.NetworkApiKey
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Named

class ApiKeyInterceptor(@NetworkApiKey private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("X-Api-Key", apiKey)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}