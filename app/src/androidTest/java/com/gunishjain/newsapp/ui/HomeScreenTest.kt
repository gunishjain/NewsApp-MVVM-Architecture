package com.gunishjain.newsapp.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.gunishjain.newsapp.navigation.Screen
import com.gunishjain.newsapp.navigation.SetupNavGraph
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SetupNavGraph(navController = navController)
        }
    }


    @Test
    fun homeScreen_content_isVisible() {
        composeTestRule.onNodeWithText("TOP HEADLINES").assertIsDisplayed()
        composeTestRule.onNodeWithText("NEWS SOURCES").assertIsDisplayed()
        composeTestRule.onNodeWithText("COUNTRIES").assertIsDisplayed()
        composeTestRule.onNodeWithText("LANGUAGES").assertIsDisplayed()
        composeTestRule.onNodeWithText("SEARCH NEWS").assertIsDisplayed()
    }

    @Test
    fun homeScreen_clickTopHeadline_navigateToHeadLines() {
        composeTestRule.onNodeWithText("TOP HEADLINES").performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, Screen.TopHeadline.route)

    }

    @Test
    fun homeScreen_clickTopHeadline_navigateToNewsSource() {
        composeTestRule.onNodeWithText("NEWS SOURCES").performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, Screen.NewsSource.route)

    }

    @Test
    fun homeScreen_clickTopHeadline_navigateToCountries() {
        composeTestRule.onNodeWithText("COUNTRIES").performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, Screen.Countries.route)

    }

    @Test
    fun homeScreen_clickTopHeadline_navigateToLanguages() {
        composeTestRule.onNodeWithText("LANGUAGES").performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, Screen.Languages.route)

    }

    @Test
    fun homeScreen_clickTopHeadline_navigateToSearchNews() {
        composeTestRule.onNodeWithText("SEARCH NEWS").performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, Screen.SearchNews.route)

    }

}