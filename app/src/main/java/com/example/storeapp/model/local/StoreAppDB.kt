package com.example.storeapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.storeapp.model.local.dao.ProductDAO
import com.example.storeapp.model.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class StoreAppDB : RoomDatabase() {

    abstract fun productDAO(): ProductDAO


    companion object {
        private var INSTANCE: StoreAppDB? = null
        fun getInstance(context: Context): StoreAppDB {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(context, StoreAppDB::class.java, "storeapp.db")
                    .allowMainThreadQueries().build()
            }

            return instance
        }
    }
}