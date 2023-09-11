package com.gunishjain.newsapp.di.component

import android.content.Context
import com.gunishjain.newsapp.NewsApplication
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.repository.TopHeadlinesRepository
import com.gunishjain.newsapp.di.ApplicationContext
import com.gunishjain.newsapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getContext() : Context

    fun getNetworkService() : NetworkService


    fun getTopHeadlinesRepository() : TopHeadlinesRepository


}