package com.gunishjain.newsapp.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen(route = "homescreen")
    object TopHeadline: Screen(route="topheadline/{countryId}"){
        fun passCountry(countryId: String) : String {
            return "topheadline/$countryId"
        }

    }
    object NewsSource: Screen(route="newssource")
    object Countries: Screen(route="countries")
    object Languages: Screen(route="languages")
    object SearchNews: Screen(route="searchnews")
}

