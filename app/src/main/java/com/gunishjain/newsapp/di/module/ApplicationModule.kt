package com.gunishjain.newsapp.di.module

import android.content.Context
import com.gunishjain.newsapp.NewsApplication
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.repository.NewsLocalRepository
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.di.ApplicationContext
import com.gunishjain.newsapp.di.BaseUrl
import com.gunishjain.newsapp.data.api.ApiKeyInterceptor
import com.gunishjain.newsapp.di.NetworkApiKey
import com.gunishjain.newsapp.utils.AppConstant
import com.gunishjain.newsapp.utils.AppConstant.API_KEY
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext() : Context {
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl() : String = AppConstant.BASE_URL

    @NetworkApiKey
    @Provides
    fun provideApiKey(): String = API_KEY

    @Provides
    @Singleton
    fun provideGsonConverterFactory() : GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@NetworkApiKey apiKey: String): ApiKeyInterceptor =
        ApiKeyInterceptor(apiKey)

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(apiKeyInterceptor).build()


    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ) :NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)

    }

    @Provides
    @Singleton
    fun provideRepository(networkService: NetworkService) : NewsRepository {
        return NewsRepository(networkService)
    }

    @Provides
    @Singleton
    fun provideLocalRepository() : NewsLocalRepository {
        return NewsLocalRepository()
    }

}