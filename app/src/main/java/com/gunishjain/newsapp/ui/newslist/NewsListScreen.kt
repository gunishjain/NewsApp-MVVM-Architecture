package com.gunishjain.newsapp.ui.newslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.ui.base.ArticleList
import com.gunishjain.newsapp.ui.base.ShowProgressBar
import com.gunishjain.newsapp.ui.base.ShowToast

@Composable
fun NewsListRoute(
    onNewsClick: (url: String) -> Unit,
    viewModel: NewsListViewModel = hiltViewModel(),
    sourceId: String? = null,
    countryId: String? = null,
    languageId: String? = null
) {
    LaunchedEffect(Unit, block = {

        if (!countryId.isNullOrEmpty()) {
            val countryIdList: List<String> = countryId.split(",")
            viewModel.fetchNewsOnCountry(countryIdList)
        } else if (!languageId.isNullOrEmpty()) {
            val languageIdList: List<String> = languageId.split(",")
            viewModel.fetchNewsOnLanguage(languageIdList)
        } else {
            viewModel.fetchNewsOnSrc(sourceId!!)
        }
    })

    val articles = viewModel.uiState.collectAsLazyPagingItems()

    Column(modifier = Modifier.padding(4.dp)) {
        NewsListScreen(articles, onNewsClick)
    }

}

@Composable
fun NewsListScreen(uiState: LazyPagingItems<Article>, onNewsClick: (url: String) -> Unit) {

    ArticleList(uiState, onNewsClick)

    uiState.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                ShowProgressBar()
            }

            loadState.refresh is LoadState.Error -> {
                val error = uiState.loadState.refresh as LoadState.Error
                ShowToast(error.error.localizedMessage!!)
            }

            loadState.append is LoadState.Loading -> {
                ShowProgressBar()
            }

            loadState.append is LoadState.Error -> {
                val error = uiState.loadState.append as LoadState.Error
                ShowToast(error.error.localizedMessage!!)
            }
        }
    }

}