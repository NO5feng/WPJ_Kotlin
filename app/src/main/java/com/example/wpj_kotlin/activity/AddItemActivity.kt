package com.example.wpj_kotlin.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.wpj_kotlin.R
import com.example.wpj_kotlin.activity.ui.theme.WPJ_KotlinTheme
import com.example.wpj_kotlin.components.BirthDatePickerDialog
import com.example.wpj_kotlin.components.ExpiredDatePickerDialog
import com.example.wpj_kotlin.components.RemindPickerDialog
import com.example.wpj_kotlin.database.NewItemViewModelFactory
import com.example.wpj_kotlin.database.database_item.Item
import com.example.wpj_kotlin.ui.AddItemUI
import com.example.wpj_kotlin.utils.DateTimeUtils
import com.example.wpj_kotlin.utils.switchTimesTamp
import com.example.wpj_kotlin.viewModels.NewItemViewModel

class AddItemActivity : ComponentActivity() {
    private val viewModel: NewItemViewModel by viewModels {
        NewItemViewModelFactory(application)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedId = intent.getIntExtra("selectedId", -1)
        if ( selectedId != -1) { viewModel.reviseDataById(selectedId) }

        enableEdgeToEdge()
        setContent {
            WPJ_KotlinTheme {
                val showBirthDialog = remember { mutableStateOf(false) }
                val showExpiredDialog = remember { mutableStateOf(false) }
                val showRemindDialog = remember { mutableStateOf(false) }

                val selectedName = viewModel.itemName.value
                val selectedBirthDate = viewModel.birthDate.value
                val selectedExpiredDate = viewModel.expiredDate.value
                val switchState = viewModel.switchState.value
                AddItemUI(
                    onCancelClick = { onBackPressed() },
                    onSaveClick = {
                        val newItem = Item(
                            itemName = selectedName,
                            birthDate = selectedBirthDate,
                            expiredDate = selectedExpiredDate,
                            remindDate = if (switchState) viewModel.remindDate.value else null
                        )
                        onBackPressed()
                        if (selectedId == -1) {
                            viewModel.insertItem(newItem)
                            Toast.makeText(this, getText(R.string.toast_add_item_success), Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.fixItem(selectedId, newItem)
                            Toast.makeText(this, getText(R.string.toast_fix_item_success), Toast.LENGTH_SHORT).show()
                        }

                    },
                    onTextChanged = { name -> viewModel.updateItemName(name) },
                    onBirthDateClick = { showBirthDialog.value = true },
                    onExpiredDateClick = { showExpiredDialog.value = true },
                    onSwitch = { check ->
                        viewModel.updateCheck(check)
                        if (check) { showRemindDialog.value = true }},
                    onAddImageClick = {
                        Toast.makeText(this, getText(R.string.add_image_remind), Toast.LENGTH_SHORT).show()
                    },
                    manufactureDateTextValue = selectedBirthDate,
                    expiredDateTextValue = selectedExpiredDate,
                    switchState = switchState,
                    itemName = selectedName
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
                        onConfirm = { type ->
                            viewModel.updateCheck(true)
                            viewModel.updateRemindDate(DateTimeUtils.getRemindDate(selectedBirthDate.switchTimesTamp(), type))
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

