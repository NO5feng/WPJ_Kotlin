package com.example.wpj_kotlin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi() {
    val context = LocalContext.current
    val backgroundColor = ContextCompat.getColor(context, R.color.yellow)
    val subBackgroundColor = ContextCompat.getColor(context, R.color.milk_white)
    val topBarTitle = ContextCompat.getString(context, R.string.home_title)
    val topAppBarColors = TopAppBarColors(
        containerColor = Color(backgroundColor),
        titleContentColor = Color.White,
        scrolledContainerColor = Color(backgroundColor),
        navigationIconContentColor = Color(backgroundColor),
        actionIconContentColor = Color(backgroundColor)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(backgroundColor))
    ) {
        TopAppBar(
            title = { Text(text = topBarTitle) },
            colors = topAppBarColors
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(subBackgroundColor))
//                .drawBehind {
//                    drawRoundRect(
//                        brush = Brush.radialGradient(),
//                        cornerRadius = CornerRadius(25.0F, 25.0F)
//                    )
//                }
        ) {
            Text(
                text = "This is the content of the page",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WPJ_KotlinTheme {
        MainUi()
    }
}