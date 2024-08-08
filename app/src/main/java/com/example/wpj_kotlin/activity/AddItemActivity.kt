package com.example.wpj_kotlin.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.ui.AddItemUI

class AddItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WPJ_KotlinTheme {
                AddItemUI(
                    onCancelClick = { onBackPressed() },
                    onSaveClick = {},
                    onTextChanged = {},
                    onManufactureDateClick = {},
                    onExpiredDateClick = {},
                    onSwitch = {},
                    onAddImageClick = {}
                )
            }
        }
    }
}

