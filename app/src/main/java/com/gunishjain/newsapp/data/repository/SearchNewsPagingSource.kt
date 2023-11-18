package com.gunishjain.newsapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.utils.AppConstant

class SearchNewsPagingSource(
    private val networkService: NetworkService,
    private val query: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: AppConstant.INITIAL_PAGE
            val response = networkService.getSearchResult(
                query = query,
                page = page,
                pageSize = AppConstant.PAGE_SIZE
            )

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == AppConstant.INITIAL_PAGE) null else page.minus(1),
                nextKey = if (response.articles.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}