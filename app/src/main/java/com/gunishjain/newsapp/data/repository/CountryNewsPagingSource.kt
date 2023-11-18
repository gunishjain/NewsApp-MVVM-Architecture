package com.gunishjain.newsapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.model.NewsResponse
import com.gunishjain.newsapp.utils.AppConstant.INITIAL_PAGE
import com.gunishjain.newsapp.utils.AppConstant.PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CountryNewsPagingSource(
    private val networkService: NetworkService,
    private val countryIdOne: String,
    private val countryIdTwo: String?,
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            var mergedArticles = mutableListOf<Article>()

            if(!countryIdTwo.isNullOrEmpty()) {
                val countryOneResponse = getArticles(countryIdOne, page, PAGE_SIZE).articles
                val countryTwoResponse = getArticles(countryIdTwo, page, PAGE_SIZE).articles
                mergedArticles.addAll(countryOneResponse)
                mergedArticles.addAll(countryTwoResponse)
                mergedArticles = mergedArticles.shuffled() as MutableList<Article>
            } else {
                val countryResponse = getArticles(countryIdOne, page, PAGE_SIZE).articles
                mergedArticles.addAll(countryResponse)
            }

            LoadResult.Page(
                data = mergedArticles,
                prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
                nextKey = if (mergedArticles.isEmpty()) null else page.plus(1),
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

    private suspend fun getArticles(countryId: String, page: Int, pageSize: Int): NewsResponse {
        return withContext(Dispatchers.Default) {
            networkService.getNewsCountry(countryId, pageSize = pageSize, page = page)
        }
    }

}