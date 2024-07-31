package com.example.wpj_kotlin.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme

@Composable
fun WelcomeUi() {
    val context = LocalContext.current
    val imageLogo = painterResource(id = R.drawable.app_text)
    val imageSlogan = painterResource(id = R.drawable.slogan)
    val backgroundColor = ContextCompat.getColor(context, R.color.yellow)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(backgroundColor))
    ) {
        Image(
            painter = imageLogo,
            contentDescription = "WelcomeUi Image",
            Modifier.size(320.dp)
                .offset(y = 100.dp)

        )
        Image(
            painter = imageSlogan,
            contentDescription = "WelcomeUi Image",
            Modifier.size(200.dp)
                .offset(y = 300.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    WPJ_KotlinTheme {
        WelcomeUi()
    }
}