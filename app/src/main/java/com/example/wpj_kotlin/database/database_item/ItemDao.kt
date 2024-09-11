package com.example.wpj_kotlin.database.database_item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM item_table WHERE id = :id")
    suspend fun getItemById(id: Int): Item?

    @Update
    suspend fun updateItem(item: Item)

    @Query("DELETE FROM item_table WHERE id = :id")
    suspend fun deleteItemById(id: Int)

    @Query("SELECT * FROM item_table")
    suspend fun getAllItems(): List<Item>
}