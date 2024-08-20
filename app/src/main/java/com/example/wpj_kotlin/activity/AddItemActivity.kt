package com.example.wpj_kotlin.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.components.BirthDatePickerDialog
import com.example.wpj_kotlin.components.ExpiredDatePickerDialog
import com.example.wpj_kotlin.components.RemindPickerDialog
import com.example.wpj_kotlin.ui.AddItemUI
import com.example.wpj_kotlin.utils.DateTimeUtils
import com.example.wpj_kotlin.utils.switchTimesTamp
import com.example.wpj_kotlin.viewModels.NewItemViewModel

class AddItemActivity : ComponentActivity() {
    private val viewModel: NewItemViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WPJ_KotlinTheme {
                val showBirthDialog = remember { mutableStateOf(false) }
                val showExpiredDialog = remember { mutableStateOf(false) }
                val showRemindDialog= remember { mutableStateOf(false) }
                val selectedBirthDate = viewModel.birthDate.value
                val selectedExpiredDate = viewModel.expiredDate.value
                val switchState = viewModel.switchState.value
                AddItemUI(
                    onCancelClick = { onBackPressed() },
                    onSaveClick = {},
                    onTextChanged = { name -> viewModel.updateItemName(name) },
                    onBirthDateClick = { showBirthDialog.value = true },
                    onExpiredDateClick = { showExpiredDialog.value = true },
                    onSwitch = { check ->
                        viewModel.updateCheck(check)
                        if (check) { showRemindDialog.value = true }},
                    onAddImageClick = {},
                    manufactureDateTextValue = selectedBirthDate,
                    expiredDateTextValue = selectedExpiredDate,
                    switchState = switchState
                )

                if (showBirthDialog.value) {
                    BirthDatePickerDialog(
                        onConfirm = { date ->
                            showBirthDialog.value = false
                            viewModel.updateBirthDate(date)
                        },
                        onCancel = { showBirthDialog.value = false },
                        initDate = selectedBirthDate
                    )
                }

                if (showExpiredDialog.value) {
                    ExpiredDatePickerDialog(
                        onConfirm = { s ->
                            val date = DateTimeUtils.getExpiredDate(selectedBirthDate.switchTimesTamp(), s)
                            viewModel.updateExpiredDate(date)
                            showExpiredDialog.value = false
                        },
                        onCancel = { showExpiredDialog.value = false }
                    )
                }

                if (showRemindDialog.value) {
                    RemindPickerDialog(
                        onConfirm = { date ->
                            viewModel.updateCheck(true)
                            showRemindDialog.value = false
                        },
                        onCancel = {
                            viewModel.updateCheck(false)
                            showRemindDialog.value = false
                        }
                    )
                }
            }
        }
    }
}

