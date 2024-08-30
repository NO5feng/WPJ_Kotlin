package com.example.wpj_kotlin.database.database_item

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM item_table WHERE id = :id")
    suspend fun getItemById(id: Int): Item?

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM item_table")
    suspend fun getAllItems(): List<Item>
}