package com.gunishjain.newsapp.ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gunishjain.newsapp.navigation.Screen


@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomOutlinedButton(text = "TOP HEADLINES", onClickAction = {
            navController.navigate(route = Screen.TopHeadline.route)
        })
        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedButton(text = "NEWS SOURCES", onClickAction = {
            navController.navigate(route = Screen.NewsSource.route)
        })

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedButton(text = "COUNTRIES", onClickAction = {
            navController.navigate(route = Screen.Countries.route)
        })

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedButton(text = "LANGUAGES", onClickAction = {
            navController.navigate(route = Screen.Languages.route)
        })

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedButton(text = "SEARCH NEWS", onClickAction = {
            navController.navigate(route = Screen.SearchNews.route)
        })

    }
}


@Composable
fun CustomOutlinedButton(
    text: String,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier,
    buttonWidth: Dp = 350.dp,
    buttonHeight: Dp = 50.dp,
    borderColor: Color = Color.Blue,
    fontSize: TextUnit = 25.sp
) {
    OutlinedButton(
        onClick = { onClickAction.invoke() },
        modifier = modifier
            .width(buttonWidth)
            .height(buttonHeight),
        border = BorderStroke(3.dp, borderColor)
    ) {
        Text(
            style = TextStyle(fontSize = fontSize),
            text = text
        )
    }
}