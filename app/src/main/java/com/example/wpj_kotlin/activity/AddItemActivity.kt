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
import com.example.wpj_kotlin.components.BirthDatePickerDialog
import com.example.wpj_kotlin.components.ExpiredDatePickerDialog
import com.example.wpj_kotlin.components.RemindPickerDialog
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
                val showExpiredDialog = remember { mutableStateOf(false) }
                val showRemindDialog= remember { mutableStateOf(false) }
                var ischeck = remember { mutableStateOf(false).value }
                val selectedBirthDate = remember { mutableStateOf(DateTimeUtils.getCurrentDateTime()) }
                AddItemUI(
                    onCancelClick = { onBackPressed() },
                    onSaveClick = {},
                    onTextChanged = {},
                    onBirthDateClick = { showBirthDialog.value = true },
                    onExpiredDateClick = { showExpiredDialog.value = true },
                    onSwitch = { check ->
                        ischeck = check
                        if (check) {
                            showRemindDialog.value = true
                        }},
                    onAddImageClick = {},
                    manufactureDateTextValue = selectedBirthDate.value,
//                    switchState = ischeck
                )

                if (showBirthDialog.value) {
                    BirthDatePickerDialog(
                        onConfirm = { date ->
                            showBirthDialog.value = false
                            selectedBirthDate.value = date
                            Log.d("BirthDatePicker_log", "date: $date")
                        },
                        onCancel = {
                            showBirthDialog.value = false
                        },
                        initDate = selectedBirthDate.value
                    )
                }

                if (showExpiredDialog.value) {
                    ExpiredDatePickerDialog(
                        onConfirm = { date ->
                            showExpiredDialog.value = false
                            selectedBirthDate.value = date
                            Log.d("BirthDatePicker_log", "date: $date")
                        },
                        onCancel = {
                            showExpiredDialog.value = false
                        }
                    )
                }

                if (showRemindDialog.value) {
                    RemindPickerDialog(
                        onConfirm = { date ->
                            showRemindDialog.value = false
                            selectedBirthDate.value = date
                            ischeck = true
                            Log.d("BirthDatePicker_log", "date: $date")
                        },
                        onCancel = {
                            ischeck = false
                            showRemindDialog.value = false
                        }
                    )
                }
            }
        }
    }
}

