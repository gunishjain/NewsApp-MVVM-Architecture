package com.gunishjain.newsapp.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "homescreen")
    object TopHeadline : Screen(route = "topheadline/{countryId}") {
        fun passCountry(countryId: String): String {
            return "topheadline/$countryId"
        }

    }

    object NewsSource : Screen(route = "newssource")
    object NewsList :
        Screen(route = "newslist?sourceId={sourceId}&countryId={countryId}&languageId={languageId}") {
        fun passData(
            sourceId: String = "",
            countryId: String = "",
            languageId: String = ""
        ): String {
            return "newslist?sourceId=$sourceId&countryId=$countryId&languageId=$languageId"
        }
    }

    object Countries : Screen(route = "countries")
    object Languages : Screen(route = "languages")
    object SearchNews : Screen(route = "searchnews")
}

