package com.gunishjain.newsapp.ui.topheadlines

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gunishjain.newsapp.data.local.entity.Article
import com.gunishjain.newsapp.data.local.entity.toArticleModel
import com.gunishjain.newsapp.ui.base.ArticleList
import com.gunishjain.newsapp.ui.base.ShowProgressBar
import com.gunishjain.newsapp.ui.base.ShowToast
import com.gunishjain.newsapp.ui.base.UiState

@Composable
fun TopHeadlineRoute(
    onNewsClick: (url: String) -> Unit,
    viewModel: TopHeadlinesViewModel = hiltViewModel()
) {
//    LaunchedEffect(Unit, block = {
//        viewModel.fetchNews()
//    })

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    Column(modifier = Modifier.padding(4.dp)) {
        TopHeadLineScreen(uiState, onNewsClick)
    }

}

@Composable
fun TopHeadLineScreen(uiState: UiState<List<Article>>, onNewsClick: (url: String) -> Unit) {

    when (uiState) {
        is UiState.Success -> {
            val apiArticleList = uiState.data.map { it.toArticleModel() }
            ArticleList(apiArticleList, onNewsClick)
        }

        is UiState.Loading -> {
            ShowProgressBar()
        }

        is UiState.Error -> {
            ShowToast(uiState.message)
        }

    }

}