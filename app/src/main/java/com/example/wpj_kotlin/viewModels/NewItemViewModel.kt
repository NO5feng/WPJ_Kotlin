package com.example.wpj_kotlin.viewModels

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wpj_kotlin.database.ItemRoomDatabase
import com.example.wpj_kotlin.database.database_item.Item
import com.example.wpj_kotlin.database.database_item.ItemDao
import com.example.wpj_kotlin.utils.DateTimeUtils
import com.example.wpj_kotlin.utils.switchTimesTamp
import kotlinx.coroutines.launch

class NewItemViewModel(application: Application) : AndroidViewModel(application) {
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

    // 操作数据库
    private val itemDao: ItemDao = ItemRoomDatabase.getDatabase(application).itemDao()
    fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insertItem(item)
        }
    }

    fun getAllItem() {
        viewModelScope.launch {
            itemDao.getAllItems()
        }
    }
}