package com.example.wpj_kotlin.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.components.BrithDatePickerDialog
import com.example.wpj_kotlin.ui.AddItemUI
import com.example.wpj_kotlin.utils.DateTimeUtils

class AddItemActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WPJ_KotlinTheme {
                val showBirthDialog = remember { mutableStateOf(false) }
                val selectedBirthDate = remember { mutableStateOf(DateTimeUtils.getCurrentDateTime()) }
                AddItemUI(
                    onCancelClick = { onBackPressed() },
                    onSaveClick = {},
                    onTextChanged = {},
                    onBirthDateClick = {
                        showBirthDialog.value = true
                    },
                    onExpiredDateClick = {},
                    onSwitch = {},
                    onAddImageClick = {},
                    manufactureDateTextValue = selectedBirthDate.value
                )

                if (showBirthDialog.value) {
                    BrithDatePickerDialog(
                        onConfirm = { date ->
                            showBirthDialog.value = false
                            selectedBirthDate.value = date
                            Log.d("DateUtils", "Formatted date: $date")
                        },
                        onCancel = {
                            showBirthDialog.value = false
                        },
                        initDate = selectedBirthDate.value
                    )
                }
            }
        }
    }
}

