package com.gunishjain.newsapp.ui.sources

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gunishjain.newsapp.data.model.Source
import com.gunishjain.newsapp.navigation.Screen
import com.gunishjain.newsapp.ui.base.ShowProgressBar
import com.gunishjain.newsapp.ui.base.ShowToast
import com.gunishjain.newsapp.ui.base.UiState

@Composable
fun NewsSourceRoute(
    navController: NavController,
    viewModel: NewsSourceViewModel = hiltViewModel()
) {
    val sources = viewModel.uiState.collectAsStateWithLifecycle()
    val uiState = sources.value

    Column(modifier = Modifier.padding(4.dp)) {
        NewsSourceScreen(uiState, onSourceClick = { sourceId ->
            navController.navigate(route = Screen.NewsList.passData(sourceId = sourceId))
        })
    }

}

@Composable
fun NewsSourceScreen(uiState: UiState<List<Source>>, onSourceClick: (source: String) -> Unit) {

    when (uiState) {
        is UiState.Success -> {
            SourceList(uiState.data, onSourceClick)
        }

        is UiState.Loading -> {
            ShowProgressBar()
        }

        is UiState.Error -> {
            ShowToast(uiState.message)
        }
    }
}


@Composable
fun SourceList(sources: List<Source>, onSourceClick: (source: String) -> Unit) {
    LazyColumn {
        items(sources.size) { index ->
            Source(source = sources[index], onSourceClick = onSourceClick)
        }
    }
}

@Composable
fun Source(source: Source, onSourceClick: (source: String) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .height(80.dp)
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                source.id?.let { onSourceClick(it) }
            }
    ) {
        Text(
            text = source.name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            fontSize = 22.sp,
        )
    }


}



