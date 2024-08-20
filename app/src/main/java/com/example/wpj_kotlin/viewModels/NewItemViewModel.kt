package com.example.wpj_kotlin.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import com.example.wpj_kotlin.utils.DateTimeUtils
import com.example.wpj_kotlin.utils.switchTimesTamp

class NewItemViewModel: ViewModel() {
    private val _itemName = mutableStateOf(DateTimeUtils.getCurrentTime())
    val itemName: State<String> = _itemName

    private val _birthDate = mutableStateOf(DateTimeUtils.getCurrentTime())
    val birthDate: State<String> = _birthDate

    private val _expiredDate = mutableStateOf(
        DateTimeUtils.getExpiredDate(DateTimeUtils.getCurrentTime().switchTimesTamp(), "1年"))
    val expiredDate: State<String> = _expiredDate

    private val _switchState = mutableStateOf(false)
    val switchState: State<Boolean> = _switchState

    private val _remindDate = mutableStateOf<String?>(null)
    val remindDate: State<String?> = _remindDate

    fun updateItemName(newName: String) {
        _itemName.value = newName
    }

    fun updateBirthDate(newDate: String) {
        _birthDate.value = newDate
    }

    fun updateExpiredDate(newDate: String) {
        _expiredDate.value = newDate
    }

    fun updateCheck(newSwitchState: Boolean) {
        _switchState.value = newSwitchState
    }

    fun updateRemindDate(newDate: String) {
        _remindDate.value = newDate

    }

}