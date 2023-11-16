package com.gunishjain.newsapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gunishjain.newsapp.data.api.NetworkService
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.utils.AppConstant.COUNTRY
import com.gunishjain.newsapp.utils.AppConstant.INITIAL_PAGE
import com.gunishjain.newsapp.utils.AppConstant.PAGE_SIZE


class NewsPagingSource(
    private val networkService: NetworkService,
    private val sourceId: String? = null,
    private val countryId: String? = null,
    private val languageId: String? = null,
    private val query: String? = null,
) :
    PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            var response = networkService.getTopHeadlines(
                country = COUNTRY,
                page = page,
                pageSize = PAGE_SIZE
            )

            if (!countryId.isNullOrEmpty()) {
                response =
                    networkService.getNewsCountry(countryId, page = page, pageSize = PAGE_SIZE)

            } else if (!languageId.isNullOrEmpty()) {
                response =
                    networkService.getNewsLanguage(languageId, page = page, pageSize = PAGE_SIZE)

            } else if (!sourceId.isNullOrEmpty()) {
                response =
                    networkService.getNewsEverything(sourceId, page = page, pageSize = PAGE_SIZE)
            } else if (!query.isNullOrEmpty()) {
                response = networkService.getSearchResult(query)
            }


            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
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