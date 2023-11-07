package com.gunishjain.newsapp.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gunishjain.newsapp.utils.AppConstant


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onClick: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ), title = { Text(text = AppConstant.APP_NAME) })
    }, content = { padding ->
        Column(modifier = Modifier.padding(padding)) {

            OutlinedButton(onClick = { onClick() }) {
                Text("TOP HEADLINES")
            }
            OutlinedButton(onClick = { onClick() }) {
                Text("NEWS SOURCES")
            }
            OutlinedButton(onClick = { onClick() }) {
                Text("COUNTRIES")
            }
            OutlinedButton(onClick = { onClick() }) {
                Text("LANGUAGES")
            }
            OutlinedButton(onClick = { onClick() }) {
                Text("SEARCH NEWS")
            }

        }
    })

}