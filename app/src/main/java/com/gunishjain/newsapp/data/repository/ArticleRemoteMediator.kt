package com.gunishjain.newsapp.data.repository

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
import com.gunishjain.newsapp.utils.AppConstant.INITIAL_PAGE
import com.gunishjain.newsapp.utils.AppConstant.PAGE_SIZE
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
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response =
                networkService.getTopHeadlines(COUNTRY, page = currentPage, pageSize = PAGE_SIZE)
            val endOfPaginationReached = (response.totalResults / PAGE_SIZE) == currentPage

            val prevPage = if (currentPage == INITIAL_PAGE) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            articleDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    articleDao.deleteAll()
                    articleRemoteKeysDao.deleteAllRemoteKeys()
                }

                articleDao.insertArticles(response.articles)
                //TODO: Issue indexing wrong for remote table

                val keys = response.articles.map { article ->
                    ArticleRemoteKeys(
                        id = article.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                articleRemoteKeysDao.addAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
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