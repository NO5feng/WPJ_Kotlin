package com.example.wpj_kotlin.database.database_item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    val itemName: String,
    val birthDate: String,
    val expiredDate: String,
    val remindDate: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)