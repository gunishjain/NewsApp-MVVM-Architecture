package com.gunishjain.newsapp.ui.newslist

import com.gunishjain.newsapp.data.model.ApiArticle
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.gunishjain.newsapp.R
import com.gunishjain.newsapp.data.model.ApiSource
import com.gunishjain.newsapp.ui.base.UiState
import org.junit.Rule
import org.junit.Test

class NewsListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_whenUiStateIsLoading_isShown() {
        composeTestRule.setContent {
            NewsListScreen(
                uiState = UiState.Loading,
                onNewsClick = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.resources.getString(R.string.loading))
            .assertExists()
    }

    @Test
    fun articles_whenUiStateIsSuccess_isShown() {
        composeTestRule.setContent {
            NewsListScreen(
                uiState = UiState.Success(testApiArticles),
                onNewsClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                testApiArticles[0].title,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    testApiArticles[5].title,
                    substring = true
                )
            )

        composeTestRule
            .onNodeWithText(
                testApiArticles[5].title,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun error_whenUiStateIsError_isShown() {
        val errorMessage = "Error Message"

        composeTestRule.setContent {
            NewsListScreen(
                uiState = UiState.Error(errorMessage),
                onNewsClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertExists()
    }

}

private val testApiArticles = listOf(
    ApiArticle(
        title = "title1",
        description = "description1",
        url = "url1",
        imageUrl = "imageUrl1",
        apiSource = ApiSource(id = "sourceId1", name = "sourceName1")
    ),
    ApiArticle(
        title = "title2",
        description = "description2",
        url = "url2",
        imageUrl = "imageUrl2",
        apiSource = ApiSource(id = "sourceId2", name = "sourceName2")
    ),
    ApiArticle(
        title = "title3",
        description = "description3",
        url = "url3",
        imageUrl = "imageUrl3",
        apiSource = ApiSource(id = "sourceId3", name = "sourceName3")
    ),
    ApiArticle(
        title = "title4",
        description = "description4",
        url = "url4",
        imageUrl = "imageUrl4",
        apiSource = ApiSource(id = "sourceId4", name = "sourceName4")
    ),
    ApiArticle(
        title = "title5",
        description = "description5",
        url = "url5",
        imageUrl = "imageUrl5",
        apiSource = ApiSource(id = "sourceId5", name = "sourceName5")
    ),
    ApiArticle(
        title = "title6",
        description = "description6",
        url = "url6",
        imageUrl = "imageUrl6",
        apiSource = ApiSource(id = "sourceId6", name = "sourceName6")
    )
)
