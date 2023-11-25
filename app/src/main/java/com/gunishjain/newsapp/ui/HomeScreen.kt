package com.gunishjain.newsapp.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.gunishjain.newsapp.navigation.Screen


@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(onClick = {
                navController.navigate(route = Screen.TopHeadline.route)
            }) {
            Text("TOP HEADLINES")
        }
        OutlinedButton(onClick = {
            navController.navigate(route = Screen.NewsSource.route)
        }) {
            Text("NEWS SOURCES")
        }
        OutlinedButton(onClick = {
            navController.navigate(route = Screen.Countries.route)
        }) {
            Text("COUNTRIES")
        }
        OutlinedButton(onClick = {
            navController.navigate(route = Screen.Languages.route)
        }) {
            Text("LANGUAGES")
        }
        OutlinedButton(onClick = {
            navController.navigate(route = Screen.SearchNews.route)
        }) {
            Text("SEARCH NEWS")
        }

    }
}