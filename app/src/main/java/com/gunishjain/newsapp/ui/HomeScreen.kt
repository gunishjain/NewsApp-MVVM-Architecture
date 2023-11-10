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
import com.gunishjain.newsapp.utils.AppConstant.COUNTRY


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
        OutlinedButton(
            onClick = {
                navController.navigate(route = Screen.TopHeadline.passCountry(COUNTRY))
            }) {
            Text("TOP HEADLINES")
        }
        OutlinedButton(onClick = {
            navController.navigate(route = Screen.NewsSource.route)
        }) {
            Text("NEWS SOURCES")
        }
        OutlinedButton(onClick = { }) {
            Text("COUNTRIES")
        }
        OutlinedButton(onClick = { }) {
            Text("LANGUAGES")
        }
        OutlinedButton(onClick = { }) {
            Text("SEARCH NEWS")
        }

    }
}