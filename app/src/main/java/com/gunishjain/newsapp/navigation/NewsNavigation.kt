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
import com.gunishjain.newsapp.ui.newslist.NewsListRoute
import com.gunishjain.newsapp.ui.search.SearchScreenRoute
import com.gunishjain.newsapp.ui.selections.CountrySelectionRoute
import com.gunishjain.newsapp.ui.selections.LanguageSelectionRoute
import com.gunishjain.newsapp.ui.sources.NewsSourceRoute
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
        ) { it ->
            val countryId = it.arguments?.getString("countryId").toString()
            TopHeadlineRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            }, countryId = countryId)
        }

        composable(route = Screen.NewsSource.route) {
            NewsSourceRoute(navController)
        }

        composable(route = Screen.NewsList.route,
            arguments = listOf(
                navArgument("sourceId") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("countryId") {
                    type = NavType.StringType
                    defaultValue = ""

                },
                navArgument("languageId") {
                    type = NavType.StringType
                    defaultValue = ""

                }
            )
        ) { it ->
            val sourceId = it.arguments?.getString("sourceId").toString()
            val countryId = it.arguments?.getString("countryId").toString()
            val languageId = it.arguments?.getString("languageId").toString()

            NewsListRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            }, sourceId = sourceId, countryId = countryId, languageId = languageId)
        }

        composable(
            route = Screen.Countries.route
        ) {
            CountrySelectionRoute(navController)
        }

        composable(
            route = Screen.Languages.route
        ) {
            LanguageSelectionRoute(navController)
        }

        composable(
            route = Screen.SearchNews.route
        ) {
            SearchScreenRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            })
        }

    }
}

fun openCustomChromeTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}