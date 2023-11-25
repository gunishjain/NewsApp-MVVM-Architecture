package com.gunishjain.newsapp.ui.sources

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.gunishjain.newsapp.R
import com.gunishjain.newsapp.data.model.Source
import com.gunishjain.newsapp.navigation.Screen
import com.gunishjain.newsapp.navigation.SetupNavGraph
import com.gunishjain.newsapp.ui.base.UiState
import com.gunishjain.newsapp.ui.topheadlines.TopHeadLineScreen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsSourceScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    lateinit var navController: TestNavHostController

    @Test
    fun loading_whenUiStateIsLoading_isShown() {
        composeTestRule.setContent {
            NewsSourceScreen(
                uiState = UiState.Loading,
                onSourceClick = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.resources.getString(R.string.loading))
            .assertExists()
    }

    @Test
    fun articles_whenUiStateIsSuccess_isShown() {
        composeTestRule.setContent {
            NewsSourceScreen(
                uiState = UiState.Success(testSources),
                onSourceClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                testSources[0].name,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    testSources[4].name,
                    substring = true
                )
            )

        composeTestRule
            .onNodeWithText(
                testSources[4].name,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun error_whenUiStateIsError_isShown() {
        val errorMessage = "Error Message"

        composeTestRule.setContent {
            NewsSourceScreen(
                uiState = UiState.Error(errorMessage),
                onSourceClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertExists()
    }


    //TODO: fix the test case for navigating to newsList.
    @Test
    fun newsSourceScreen_clickSource_navigateToNewsList() {

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SetupNavGraph(navController = navController)
            NewsSourceScreen(
                uiState = UiState.Success(testSources),
                onSourceClick = {}
            )
        }

        composeTestRule.onNodeWithText("name1").performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, Screen.NewsList.route)
    }

}

private val testSources = listOf(
    Source(
        id = "id1",
        name = "name1"
    ),
    Source(
        id = "id2",
        name = "name2"
    ),
    Source(
        id = "id3",
        name = "name3"
    ),
    Source(
        id = "id4",
        name = "name4"
    ),
    Source(
        id = "id5",
        name = "name5"
    )
)

