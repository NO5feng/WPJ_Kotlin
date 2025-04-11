package com.example.wpj_kotlin.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.Fade
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wpj_kotlin.ui.ClockUi


class ClockActivity : ComponentActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.enterTransition = Fade().apply {
            duration = 350
        }
        setContent {
            ClockUi()
        }
    }
}

