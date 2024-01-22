package com.gunishjain.newsapp.utils

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.local.DatabaseService
import com.gunishjain.newsapp.utils.AppConstant.COUNTRY
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import toArticleEntity


@HiltWorker
class NewsWorker @AssistedInject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        lateinit var result: Result

        runCatching {
            val articles = networkService.getTopHeadlines(COUNTRY).apiArticles.map { apiArticle ->
                apiArticle.toArticleEntity()
            }
            databaseService.deleteAllAndInsertAll(articles)
        }.onSuccess {
            result = Result.success()
        }.onFailure {
            result = Result.retry()
        }
        return result

    }

}