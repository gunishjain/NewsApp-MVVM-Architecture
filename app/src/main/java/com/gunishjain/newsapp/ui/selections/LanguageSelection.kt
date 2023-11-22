package com.gunishjain.newsapp.ui.selections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gunishjain.newsapp.data.model.Language
import com.gunishjain.newsapp.data.model.LanguageState
import com.gunishjain.newsapp.navigation.Screen
import com.gunishjain.newsapp.ui.base.ShowProgressBar
import com.gunishjain.newsapp.ui.base.ShowToast
import com.gunishjain.newsapp.ui.base.UiState

@Composable
fun LanguageSelectionRoute(
    navController: NavController,
    viewModel: LanguageSelectionViewModel = hiltViewModel()
) {
    val languages = viewModel.uiStateLanguage.collectAsStateWithLifecycle()
    var selectionState by remember { mutableStateOf(LanguageState()) }
    val uiState = languages.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {

        LanguageSelectionScreen(
            uiState,
            selectionState = selectionState,
            onItemClick = {
                selectionState = selectionState.copy(
                    selectedLanguages = if (selectionState.selectedLanguages.contains(it)) {
                        selectionState.selectedLanguages - it
                    } else {
                        if (selectionState.selectedLanguages.size < 2) {
                            selectionState.selectedLanguages + it
                        } else {
                            selectionState.selectedLanguages
                        }
                    }
                )
            }
        )

        Card(
            shape = RoundedCornerShape(25),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(width = 125.dp, height = 80.dp)
                .padding(20.dp)
        ) {
            FloatingActionButton(

                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val languageString =
                        selectionState.selectedLanguages.joinToString(",") { it.id.orEmpty() }
                    navController.navigate(route = Screen.NewsList.passData(languageId = languageString))

                }
            ) {
                Text(text = "PROCEED")
            }
        }
    }
}


@Composable
fun LanguageSelectionScreen(
    uiState: UiState<List<Language>>,
    onItemClick: (Language) -> Unit,
    selectionState: LanguageState
) {

    when (uiState) {
        is UiState.Success -> {
            LanguageList(uiState.data, onItemClick, selectionState)
        }

        is UiState.Loading -> {
            ShowProgressBar()
        }

        is UiState.Error -> {
            ShowToast(uiState.message)
        }
    }
}


@Composable
fun LanguageList(
    items: List<Language>,
    onItemClick: (Language) -> Unit,
    selectionState: LanguageState,
) {
    LazyColumn {
        items(items.size) { index ->
            LanguageItem(
                item = items[index],
                isSelected = selectionState.selectedLanguages.contains(items[index]),
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun LanguageItem(item: Language, onItemClick: (Language) -> Unit, isSelected: Boolean) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .height(80.dp)
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(item)
            }
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.name)
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.Green,
                    modifier = Modifier.size(15.dp)
                )
            }
        }

    }

}