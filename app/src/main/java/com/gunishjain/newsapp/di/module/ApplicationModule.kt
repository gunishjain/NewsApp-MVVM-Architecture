package com.gunishjain.newsapp.di.module

import android.content.Context
import androidx.room.Room
import com.gunishjain.newsapp.data.api.ApiKeyInterceptor
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.local.ArticleDatabase
import com.gunishjain.newsapp.di.BaseUrl
import com.gunishjain.newsapp.di.NetworkApiKey
import com.gunishjain.newsapp.utils.AppConstant
import com.gunishjain.newsapp.utils.AppConstant.API_KEY
import com.gunishjain.newsapp.utils.AppConstant.ARTICLE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = AppConstant.BASE_URL

    @NetworkApiKey
    @Provides
    fun provideApiKey(): String = API_KEY

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

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
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)

    }

    @Provides
    @Singleton
    fun provideArticleDatabase(
        @ApplicationContext context: Context,
    ): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            ARTICLE_DATABASE
        ).build()
    }


}