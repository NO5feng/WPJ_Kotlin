package com.example.wpj_kotlin.database.database_item

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    val itemName: String,
    val birthDate: String,
    val expiredDate: String,
    val remindDate: String? = null,
    val imagePath: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

data class ItemCard(
    val id: Int,
    val itemName: String,
    val type: Int? = 1,
    val day: String,
    val imagePath: Bitmap? = null
)