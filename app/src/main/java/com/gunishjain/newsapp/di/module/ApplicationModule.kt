package com.gunishjain.newsapp.di.module

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.gunishjain.newsapp.data.api.ApiKeyInterceptor
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.local.DatabaseService
import com.gunishjain.newsapp.data.local.NewsDatabase
import com.gunishjain.newsapp.data.local.NewsDatabaseService
import com.gunishjain.newsapp.di.BaseUrl
import com.gunishjain.newsapp.di.DatabaseName
import com.gunishjain.newsapp.di.NetworkApiKey
import com.gunishjain.newsapp.utils.AppConstant
import com.gunishjain.newsapp.utils.AppConstant.API_KEY
import com.gunishjain.newsapp.utils.DefaultDispatcherProvider
import com.gunishjain.newsapp.utils.DefaultNetworkHelper
import com.gunishjain.newsapp.utils.DispatcherProvider
import com.gunishjain.newsapp.utils.NetworkHelper
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
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return DefaultNetworkHelper(context)
    }

    @DatabaseName
    @Provides
    fun provideDatabaseName(): String = "news-database"

    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDatabaseService(appDatabase: NewsDatabase): DatabaseService {
        return NewsDatabaseService(appDatabase)
    }

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }


}