package com.example.wpj_kotlin.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.ui.AddItemUI
import com.example.wpj_kotlin.ui.BrithDatePickerDialog

class AddItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WPJ_KotlinTheme {
                val showBirthDialog = remember { mutableStateOf(false) }
                AddItemUI(
                    onCancelClick = { onBackPressed() },
                    onSaveClick = {},
                    onTextChanged = {},
                    onBirthDateClick = {
                        showBirthDialog.value = true
                    },
                    onExpiredDateClick = {},
                    onSwitch = {},
                    onAddImageClick = {}
                )

                if (showBirthDialog.value) {
                    BrithDatePickerDialog(
                        onConfirm = { date ->
                            showBirthDialog.value = false
                        },
                        onCancel = {
                            showBirthDialog.value = false
                        }
                    )
                }
            }
        }
    }
}

