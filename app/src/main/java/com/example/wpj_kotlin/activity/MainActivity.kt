package com.example.wpj_kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.ui.MainUi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WPJ_KotlinTheme {
                MainUi(
                    onTextChanged = { text ->
                        Log.d("MainActivity", "Text changed: $text")
                    },
                    onImageClick = {
                        startActivity(Intent(this, AddItemActivity::class.java))
                    }
                )
            }
        }
    }
}
