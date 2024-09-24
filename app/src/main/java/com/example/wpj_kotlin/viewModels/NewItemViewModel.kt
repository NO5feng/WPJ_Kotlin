package com.example.wpj_kotlin.viewModels

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wpj_kotlin.database.ItemRoomDatabase
import com.example.wpj_kotlin.database.database_item.Item
import com.example.wpj_kotlin.database.database_item.ItemCard
import com.example.wpj_kotlin.database.database_item.ItemDao
import com.example.wpj_kotlin.utils.DateTimeUtils
import com.example.wpj_kotlin.utils.getBitmapFromFile
import com.example.wpj_kotlin.utils.switchTimesTamp
import kotlinx.coroutines.launch
import kotlin.math.abs

class NewItemViewModel(application: Application) : AndroidViewModel(application) {
    private val _itemName = mutableStateOf("")
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

    private val _itemCards = mutableStateOf<List<ItemCard>>(emptyList())
    val itemCards: State<List<ItemCard>> = _itemCards

    private val _imagePath = mutableStateOf<String?>(null)
    val imagePath: State<String?> = _imagePath

    fun updateImagePath(imagePath: String) {
        _imagePath.value = imagePath
    }

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

    fun fixItem(id: Int, item: Item) {
        viewModelScope.launch {
            itemDao.fixItemById(id, item.itemName, item.birthDate, item.expiredDate,
                item.remindDate.toString(), item.imagePath.toString())
        }
    }

    fun getAllItem() {
        viewModelScope.launch {
            val items = itemDao.getAllItems()  // 从数据库中获取所有 items
            _itemCards.value = getItemCards(items)  // 使用你的转换函数
        }
    }

    fun deleteItemById(id: Int) {
        viewModelScope.launch {
            itemDao.deleteItemById(id)
            getAllItem()
        }
    }

    fun reviseDataById(id: Int) {
        viewModelScope.launch {
            val newDate = itemDao.getItemById(id)
            newDate?.let {
                updateItemName(newDate.itemName)
                updateBirthDate(newDate.birthDate)
                updateExpiredDate(newDate.expiredDate)
                updateCheck(newDate.remindDate.toString() != "null" && newDate.remindDate.toString() != "")
                updateRemindDate(newDate.remindDate.toString())
                newDate.imagePath?.let {
                    updateImagePath(newDate.imagePath)
                }
            }
        }
    }

    private fun getItemCards(items: List<Item>? = null): List<ItemCard> {
        val currentTime = DateTimeUtils.getCurrentTime().switchTimesTamp()
        return items?.groupBy { it.itemName }?.flatMap { (itemName, itemList) ->
            itemList.map { item ->
                val expiredTime = item.expiredDate.switchTimesTamp()
                val type = if (expiredTime >= currentTime) 1 else 2
                val dayDifference = abs(((expiredTime - currentTime) / (60 * 60 * 24))).toString()
                ItemCard(
                    id = item.id,
                    itemName = itemName,
                    type = type,
                    day = dayDifference,
                    imagePath = item.imagePath?.let { getBitmapFromFile(it) }
                )
            }
        } ?: emptyList()
    }
}