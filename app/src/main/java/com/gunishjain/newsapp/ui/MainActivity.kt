package com.gunishjain.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gunishjain.newsapp.navigation.SetupNavGraph
import com.gunishjain.newsapp.ui.theme.NewsAppTheme
import com.gunishjain.newsapp.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                navController = rememberNavController()
                Scaffold(topBar = {
                    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White
                    ), title = { Text(text = AppConstant.APP_NAME) })
                }) { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                    ) {
                        SetupNavGraph(navController = navController)
                    }
                }

            }
        }

    }
}