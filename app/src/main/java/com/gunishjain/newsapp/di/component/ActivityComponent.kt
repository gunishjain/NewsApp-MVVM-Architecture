package com.gunishjain.newsapp.di.component

import com.gunishjain.newsapp.di.ActivityScope
import com.gunishjain.newsapp.di.module.ActivityModule
import com.gunishjain.newsapp.ui.sources.NewsSourceActivity
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlinesActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity : TopHeadlinesActivity)
    fun inject(activity: NewsSourceActivity)
}