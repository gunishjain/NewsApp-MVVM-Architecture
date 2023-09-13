package com.gunishjain.newsapp.di.module

import android.content.Context
import com.gunishjain.newsapp.NewsApplication
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.di.ApplicationContext
import com.gunishjain.newsapp.di.BaseUrl
import com.gunishjain.newsapp.utils.AppConstant
import dagger.Module
import dagger.Provides
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

    @Provides
    @Singleton
    fun provideGsonConverterFactory() : GsonConverterFactory = GsonConverterFactory.create()


    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ) :NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)

    }

    @Provides
    @Singleton
    fun provideRepository(networkService: NetworkService) : NewsRepository {
        return NewsRepository(networkService)
    }

}