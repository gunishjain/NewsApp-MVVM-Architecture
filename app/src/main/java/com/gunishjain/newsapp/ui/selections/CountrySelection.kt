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
import com.gunishjain.newsapp.data.model.Country
import com.gunishjain.newsapp.data.model.SelectionState
import com.gunishjain.newsapp.navigation.Screen
import com.gunishjain.newsapp.ui.base.ShowProgressBar
import com.gunishjain.newsapp.ui.base.ShowToast
import com.gunishjain.newsapp.ui.base.UiState

@Composable
fun CountrySelectionRoute(
    navController: NavController,
    viewModel: SelectionsViewModel = hiltViewModel()
) {
    val countries = viewModel.uiStateCountry.collectAsStateWithLifecycle()
    var selectionState by remember { mutableStateOf(SelectionState()) }
    val uiState = countries.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {

        CountrySelectionScreen(
            uiState,
            selectionState = selectionState,
            onItemClick = {
                selectionState = selectionState.copy(
                    selectedCountries = if (selectionState.selectedCountries.contains(it)) {
                        selectionState.selectedCountries - it
                    } else {
                        if (selectionState.selectedCountries.size < 2) {
                            selectionState.selectedCountries + it
                        } else {
                            selectionState.selectedCountries
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
                    if (selectionState.selectedCountries.size == 2) {
                        val countriesString =
                            selectionState.selectedCountries.joinToString(",") { it.id.orEmpty() }
                        navController.navigate(route = Screen.NewsList.passData(countryId = countriesString))

                    } else {
//                        ShowToast(text = "Please Select Two Countries")
                    }
                }
            ) {
                Text(text = "PROCEED")
            }
        }
    }
}


@Composable
fun CountrySelectionScreen(
    uiState: UiState<List<Country>>,
    onItemClick: (Country) -> Unit,
    selectionState: SelectionState
) {

    when (uiState) {
        is UiState.Success -> {
            CountryList(uiState.data, onItemClick, selectionState)
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
fun CountryList(
    items: List<Country>,
    onItemClick: (Country) -> Unit,
    selectionState: SelectionState,
) {
    LazyColumn {
        items(items.size) { index ->
            CountryItem(
                item = items[index],
                isSelected = selectionState.selectedCountries.contains(items[index]),
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun CountryItem(item: Country, onItemClick: (Country) -> Unit, isSelected: Boolean) {
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