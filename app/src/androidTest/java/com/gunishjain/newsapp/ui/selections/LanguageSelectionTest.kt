package com.gunishjain.newsapp.ui.selections

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.gunishjain.newsapp.R
import com.gunishjain.newsapp.data.model.Language
import com.gunishjain.newsapp.data.model.LanguageState
import com.gunishjain.newsapp.ui.base.UiState
import org.junit.Rule
import org.junit.Test

class LanguageSelectionTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_whenUiStateIsLoading_isShown() {

        val selectionState = LanguageState(emptyList())

        composeTestRule.setContent {
            LanguageSelectionScreen(
                uiState = UiState.Loading,
                onItemClick = {},
                selectionState
            )
        }

        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.resources.getString(R.string.loading))
            .assertExists()
    }

    @Test
    fun language_whenUiStateIsSuccess_isShown() {

        val selectionState = LanguageState(emptyList())

        composeTestRule.setContent {
            LanguageSelectionScreen(
                uiState = UiState.Success(testLanguages),
                onItemClick = {},
                selectionState
            )
        }

        composeTestRule
            .onNodeWithText(
                testLanguages[0].name,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    testLanguages[4].name,
                    substring = true
                )
            )

        composeTestRule
            .onNodeWithText(
                testLanguages[4].name,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun error_whenUiStateIsError_isShown() {
        val errorMessage = "Error Message"
        val selectionState = LanguageState(emptyList())

        composeTestRule.setContent {
            LanguageSelectionScreen(
                uiState = UiState.Error(errorMessage),
                onItemClick = {},
                selectionState
            )
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertExists()
    }

}

private val testLanguages = listOf(
    Language(
        id = "languageId1",
        name = "languageName1",
    ),
    Language(
        id = "languageId2",
        name = "languageName2",
    ),
    Language(
        id = "languageId3",
        name = "languageName3",
    ),
    Language(
        id = "languageId4",
        name = "languageName4",
    ),
    Language(
        id = "languageId5",
        name = "languageName5",
    ),
)
