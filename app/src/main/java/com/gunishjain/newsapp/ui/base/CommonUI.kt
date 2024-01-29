package com.gunishjain.newsapp.ui.base

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.gunishjain.newsapp.data.model.Article
import com.gunishjain.newsapp.data.model.Source

@Composable
fun ShowProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ShowToast(text: String) {
    Toast.makeText(LocalContext.current, text, Toast.LENGTH_LONG).show()
}


@Composable
fun ArticleList(articles: LazyPagingItems<Article>, onNewsClick: (url: String) -> Unit) {
    LazyColumn {
        items(articles.itemCount) { index ->
            Article(article = articles[index]!!, onNewsClick = onNewsClick)
        }
    }
}

@Composable
fun Article(article: Article, onNewsClick: (url: String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            if (article.url.isNotEmpty()) {
                onNewsClick(article.url)
            }
        }) {
        NewsImageBanner(article)
        NewsTitle(article.title)
        NewsDescription(article.description)
        NewsSource(article.source)
    }

}

@Composable
fun NewsImageBanner(article: Article) {
    AsyncImage(
        model = article.urlToImage,
        contentDescription = article.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    )
}

@Composable
fun NewsTitle(title: String?) {
    if (!title.isNullOrEmpty()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            maxLines = 2,
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
fun NewsDescription(description: String?) {
    if (!description.isNullOrEmpty()) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            maxLines = 2,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun NewsSource(source: Source?) {
    if (source != null) {
        Text(
            text = source.name,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray,
            maxLines = 1,
            modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp)
        )
    }
}