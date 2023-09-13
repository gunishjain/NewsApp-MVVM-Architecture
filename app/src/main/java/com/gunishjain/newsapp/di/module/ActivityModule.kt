package com.gunishjain.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gunishjain.newsapp.data.repository.TopHeadlinesRepository
import com.gunishjain.newsapp.di.ActivityContext
import com.gunishjain.newsapp.ui.base.ViewModelProviderFactory
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesAdapter
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext() : Context {
        return activity
    }

    @Provides
    fun provideTopHeadlinesViewModel(topHeadlinesRepository : TopHeadlinesRepository) : TopHeadlinesViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadlinesViewModel::class) {
            TopHeadlinesViewModel(topHeadlinesRepository)
            })[TopHeadlinesViewModel::class.java]

    }

    @Provides
    fun provideTopHeadlinesAdapter() = TopHeadlinesAdapter(ArrayList())


}