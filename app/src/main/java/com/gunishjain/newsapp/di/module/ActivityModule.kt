package com.gunishjain.newsapp.di.module

import com.gunishjain.newsapp.data.model.Country
import com.gunishjain.newsapp.data.model.Language
import com.gunishjain.newsapp.ui.base.genericrecyclerview.BaseAdapter
import com.gunishjain.newsapp.ui.sources.NewsSourceAdapter
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideTopHeadlinesAdapter() = TopHeadlinesAdapter(ArrayList())

    @ActivityScoped
    @Provides
    fun provideNewsSourceAdapter() = NewsSourceAdapter(ArrayList())

    @ActivityScoped
    @Provides
    fun provideGenericCountryAdapter() = BaseAdapter<Country>()

    @ActivityScoped
    @Provides
    fun provideGenericLanguageAdapter() = BaseAdapter<Language>()


}