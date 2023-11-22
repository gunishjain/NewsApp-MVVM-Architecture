package com.gunishjain.newsapp.ui.newslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.ui.base.ArticleList
import com.gunishjain.newsapp.ui.base.ShowProgressBar
import com.gunishjain.newsapp.ui.base.ShowToast
import com.gunishjain.newsapp.ui.base.UiState

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
            viewModel.fetchNewsByCountry(countryIdList)
        } else if (!languageId.isNullOrEmpty()) {
            val languageIdList: List<String> = languageId.split(",")
            viewModel.fetchNewsByLanguage(languageIdList)
        } else {
            viewModel.fetchNewsBySrc(sourceId!!)
        }
    })

    val articles = viewModel.uiState.collectAsStateWithLifecycle()
    val uiState = articles.value

    Column(modifier = Modifier.padding(4.dp)) {
        NewsListScreen(uiState, onNewsClick)
    }

}

@Composable
fun NewsListScreen(uiState: UiState<List<Article>>, onNewsClick: (url: String) -> Unit) {

    when (uiState) {
        is UiState.Success -> {
            ArticleList(uiState.data, onNewsClick)
        }

        is UiState.Loading -> {
            ShowProgressBar()
        }

        is UiState.Error -> {
            ShowToast(uiState.message)
        }

    }
}