package com.example.wpj_kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wpj_kotlin.database.database_item.Item
import com.example.wpj_kotlin.database.database_item.ItemDao

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemRoomDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: ItemRoomDatabase? = null

        fun getDatabase(context: Context): ItemRoomDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ItemRoomDatabase::class.java, "WPJ")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}