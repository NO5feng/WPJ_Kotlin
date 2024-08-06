package com.example.wpj_kotlin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemUI() {
    val context = LocalContext.current
    val yellow = ContextCompat.getColor(context, R.color.yellow)
    val white = ContextCompat.getColor(context, R.color.milk_white)
    val pink = ContextCompat.getColor(context, R.color.pink)
    val topBarTitle = context.getString(R.string.add_title)

    val topAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color(white),
        titleContentColor = Color.Black,
        scrolledContainerColor = Color(white),
        navigationIconContentColor = Color(white),
        actionIconContentColor = Color(white)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(white))
            .statusBarsPadding()
    ) {

        Row {
            Text(text = topBarTitle)
//            modifier = Modifier
//                .statusBarsPadding()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WPJ_KotlinTheme {
        AddItemUI()
    }
}