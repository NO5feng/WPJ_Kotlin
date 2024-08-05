package com.example.wpj_kotlin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val topBarTitle = context.getString(R.string.home_title)
    val editText = context.getString(R.string.home_edit_hint)
    var text by remember { mutableStateOf("") }

    val topAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color(backgroundColor),
        titleContentColor = Color.Black,
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(Color(subBackgroundColor))
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    value = text,
                    onValueChange = { newText -> text = newText },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(backgroundColor),
                        disabledIndicatorColor = Color(backgroundColor),
                        unfocusedIndicatorColor = Color(backgroundColor),
                        focusedContainerColor = Color(backgroundColor),
                        disabledContainerColor = Color(backgroundColor),
                        unfocusedContainerColor = Color(backgroundColor),
                    ),
                    label = { Text(text = editText) },
                    modifier = Modifier.padding(16.dp),
                    singleLine = true // 使输入框为单行
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    WPJ_KotlinTheme {
        MainUi()
    }
}