package com.gunishjain.newsapp.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.local.ArticleDatabase
import com.gunishjain.newsapp.data.local.entity.ArticleRemoteKeys
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.utils.AppConstant.COUNTRY
import com.gunishjain.newsapp.utils.AppConstant.PAGE_SIZE
import com.gunishjain.newsapp.utils.generateUniqueHash
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator @Inject constructor(
    private val networkService: NetworkService,
    private val articleDatabase: ArticleDatabase
) : RemoteMediator<Int, Article>() {

    private val articleDao = articleDatabase.articleDao()
    private val articleRemoteKeysDao = articleDatabase.articleRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("GUNISH", "REFRESH")
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {

                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    Log.d("GUNISH", "APPEND")
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val response = networkService.getTopHeadlines(
                country = COUNTRY,
                page = currentPage,
                pageSize = PAGE_SIZE
            )
            val endOfPaginationReached = (currentPage * PAGE_SIZE) >= response.totalResults

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            articleDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    articleDao.deleteAll()
                    articleRemoteKeysDao.deleteAllRemoteKeys()
                }
                val articlesWithUniqueID = response.articles.map { article ->
                    val id = generateUniqueHash(article)

                    Article(
                        id = id,
                        title = article.title,
                        description = article.description,
                        url = article.url,
                        urlToImage = article.urlToImage,
                        source = article.source
                    )

                }

                val keys = articlesWithUniqueID.map { article ->
                    ArticleRemoteKeys(
                        id = article.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                articleRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                articleDao.insertArticles(articlesWithUniqueID)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): ArticleRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                articleRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Article>
    ): ArticleRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                articleRemoteKeysDao.getRemoteKeys(id = article.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Article>
    ): ArticleRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                articleRemoteKeysDao.getRemoteKeys(id = article.id)
            }
    }

}