package com.gunishjain.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gunishjain.newsapp.data.model.Country
import com.gunishjain.newsapp.data.model.Language
import com.gunishjain.newsapp.data.model.Source
import com.gunishjain.newsapp.data.repository.NewsLocalRepository
import com.gunishjain.newsapp.data.repository.NewsRepository
import com.gunishjain.newsapp.di.ActivityContext
import com.gunishjain.newsapp.ui.base.ViewModelProviderFactory
import com.gunishjain.newsapp.ui.newslist.NewsListViewModel
import com.gunishjain.newsapp.ui.search.SearchNewsViewModel
import com.gunishjain.newsapp.ui.selections.SelectionsViewModel
import com.gunishjain.newsapp.ui.sources.NewsSourceAdapter
import com.gunishjain.newsapp.ui.sources.NewsSourceViewModel
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesAdapter
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesViewModel
import com.gunishjain.newsapp.utils.genericrecyclerview.BaseAdapter
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
    fun provideTopHeadlinesViewModel(newsRepository : NewsRepository) : TopHeadlinesViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadlinesViewModel::class) {
            TopHeadlinesViewModel(newsRepository)
            })[TopHeadlinesViewModel::class.java]

    }

    @Provides
    fun provideNewsSourceViewModel(newsRepository: NewsRepository) : NewsSourceViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(NewsSourceViewModel::class) {
                NewsSourceViewModel(newsRepository)
            })[NewsSourceViewModel::class.java]
    }

    @Provides
    fun provideNewsListViewModel(newsRepository: NewsRepository) : NewsListViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(NewsListViewModel::class) {
                NewsListViewModel(newsRepository)
            })[NewsListViewModel::class.java]
    }

    @Provides
    fun provideSelectionViewModel(newsLocalRepository: NewsLocalRepository) : SelectionsViewModel {
        return ViewModelProvider(activity,
        ViewModelProviderFactory(SelectionsViewModel::class){
            SelectionsViewModel(newsLocalRepository)
        })[SelectionsViewModel::class.java]
    }

    @Provides
    fun provideSearchNewsViewModel(newsRepository: NewsRepository) : SearchNewsViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(SearchNewsViewModel::class){
                SearchNewsViewModel(newsRepository)
            })[SearchNewsViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlinesAdapter() = TopHeadlinesAdapter(ArrayList())

    @Provides
    fun provideNewsSourceAdapter() = NewsSourceAdapter(ArrayList())

    @Provides
    fun provideGenericCountryAdapter() = BaseAdapter<Country>()

    @Provides
    fun provideGenericLanguageAdapter() = BaseAdapter<Language>()


}