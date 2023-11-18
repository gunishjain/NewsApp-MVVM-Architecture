package com.gunishjain.newsapp.ui.topheadlines

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
fun TopHeadlineRoute(
    onNewsClick: (url: String) -> Unit,
    viewModel: TopHeadlinesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit, block = {
        viewModel.fetchNews()
    })

    val articles = viewModel.uiState.collectAsLazyPagingItems()

    Column(modifier = Modifier.padding(4.dp)) {
        TopHeadLineScreen(articles, onNewsClick)
    }

}

@Composable
fun TopHeadLineScreen(uiState: LazyPagingItems<Article>, onNewsClick: (url: String) -> Unit) {

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