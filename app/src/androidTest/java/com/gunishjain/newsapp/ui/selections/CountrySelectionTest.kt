package com.gunishjain.newsapp.ui.selections

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.gunishjain.newsapp.R
import com.gunishjain.newsapp.data.model.Country
import com.gunishjain.newsapp.data.model.SelectionState
import com.gunishjain.newsapp.ui.base.UiState
import org.junit.Rule
import org.junit.Test

class CountrySelectionTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_whenUiStateIsLoading_isShown() {

        val selectionState = SelectionState(emptyList())

        composeTestRule.setContent {
            CountrySelectionScreen(
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
    fun countries_whenUiStateIsSuccess_isShown() {

        val selectionState = SelectionState(emptyList())

        composeTestRule.setContent {
            CountrySelectionScreen(
                uiState = UiState.Success(testCountries),
                onItemClick = {},
                selectionState
            )
        }

        composeTestRule
            .onNodeWithText(
                testCountries[0].name,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    testCountries[4].name,
                    substring = true
                )
            )

        composeTestRule
            .onNodeWithText(
                testCountries[4].name,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun error_whenUiStateIsError_isShown() {
        val errorMessage = "Error Message"
        val selectionState = SelectionState(emptyList())

        composeTestRule.setContent {
            CountrySelectionScreen(
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

private val testCountries = listOf(
    Country(
        id = "countryId1",
        name = "countryName1",
    ),
    Country(
        id = "countryId2",
        name = "countryName2",
    ),
    Country(
        id = "countryId3",
        name = "countryName3",
    ),
    Country(
        id = "countryId4",
        name = "countryName4",
    ),
    Country(
        id = "countryId5",
        name = "countryName5",
    ),
)

