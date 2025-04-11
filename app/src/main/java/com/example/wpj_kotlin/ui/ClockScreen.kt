package com.example.wpj_kotlin.ui

import add_clock
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ClockUi() {
    val context = LocalContext.current
    val subBackgroundColor = ContextCompat.getColor(context, R.color.milk_white)
    val backgroundColor = ContextCompat.getColor(context, R.color.pink)
    val blue = ContextCompat.getColor(context, R.color.blue)
    val topBarTitle = context.getString(R.string.clock_title)
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
            title = {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = topBarTitle,
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentSize(Alignment.Center),
                    )
                }
            },
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f) // Use weight to distribute the remaining space
                        .verticalScroll(rememberScrollState())
                ) {
//                    if (itemCards.isNotEmpty()) {
//                        for (itemCard in itemCards ) {
//                            ItemCard(
//                                itemCard = itemCard,
//                                slideStart = { i -> slideStart(i) },
//                                slideEnd = { i -> slideEnd(i) },
//                            )
//                        }
//                    }
                }

                Icon(
                    imageVector = add_clock,
                    contentDescription = null,
                    tint = Color(backgroundColor),
                    modifier = Modifier
                        .size(60.dp)
                        .combinedClickable( onClick = {  } )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClockPreview() {
    WPJ_KotlinTheme {
        ClockUi()
    }
}