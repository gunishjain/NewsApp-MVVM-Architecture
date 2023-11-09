package com.gunishjain.newsapp.navigation

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gunishjain.newsapp.ui.HomeScreen
import com.gunishjain.newsapp.ui.topheadlines.TopHeadlineRoute

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.TopHeadline.route,
            arguments = listOf(navArgument("countryId") {
                type = NavType.StringType
            })
        ) {
            val countryId = it.arguments?.getString("countryId").toString()
            TopHeadlineRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            }, countryId = countryId)
        }
    }
}

fun openCustomChromeTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}