package com.gunishjain.newsapp.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.gunishjain.newsapp.ui.newslist.NewsListScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenRoute(
    onNewsClick: (url: String) -> Unit,
    viewModel: SearchNewsViewModel = hiltViewModel(),
) {

    val searchResult = viewModel.uiState.collectAsLazyPagingItems()
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(4.dp)) {

        SearchBar(
            query = text,
            onQueryChange = {
                text = it
                viewModel.onQuerySearch(it)
            },
            onSearch = {
                active = false
            },
            active = active,
            placeholder = {
                Text(text = "Search News")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            },
            content = {
                NewsListScreen(searchResult, onNewsClick)
            },
            onActiveChange = {
                active = it
            },
            tonalElevation = 0.dp
        )
    }

}