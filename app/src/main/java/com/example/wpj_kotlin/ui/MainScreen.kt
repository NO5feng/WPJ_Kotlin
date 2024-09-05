package com.example.wpj_kotlin.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.components.ItemCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainUi(
    onTextChanged: (String) -> Unit, onImageClick: () -> Unit
) {
    val context = LocalContext.current
    val backgroundColor = ContextCompat.getColor(context, R.color.yellow)
    val subBackgroundColor = ContextCompat.getColor(context, R.color.milk_white)
    val cursor = ContextCompat.getColor(context, R.color.pink)
    val topBarTitle = context.getString(R.string.home_title)
    val editText = context.getString(R.string.home_edit_hint)
    val addBtn = painterResource(id = R.drawable.add_button)

    var text by remember { mutableStateOf(TextFieldValue()) }

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
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp)
            ) {
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onTextChanged(it.text)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color(backgroundColor),
                        unfocusedContainerColor = Color(backgroundColor),
                        cursorColor = Color(cursor),
                        focusedLeadingIconColor = Color.Black,
                        unfocusedLeadingIconColor = Color.Black,
                        focusedPlaceholderColor = Color.Black,
                        unfocusedPlaceholderColor = Color.Black
                    ),
                    placeholder = { Text(text = editText) },
                    singleLine = true, // 使输入框为单行
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .width(300.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 50.dp, topEnd = 50.dp,
                                bottomStart = 50.dp, bottomEnd = 50.dp
                            )
                        )
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f) // Use weight to distribute the remaining space
                        .verticalScroll(rememberScrollState())
                ) {
                    ItemCard()
                    ItemCard()
//                    Text(text123, modifier = Modifier.padding(30.dp))
                    repeat(10) {
                        Text("Item $it", modifier = Modifier.padding(30.dp))
                    }
                }

                Image(
                    painter = addBtn,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .combinedClickable( onClick = { onImageClick() } )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    WPJ_KotlinTheme {
        MainUi(
            onTextChanged = {},
            onImageClick = {}
        )
    }
}