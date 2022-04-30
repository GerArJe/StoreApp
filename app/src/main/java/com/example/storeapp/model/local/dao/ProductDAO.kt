package com.example.storeapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.storeapp.model.entity.Product

@Dao
interface ProductDAO {

    @Query("select * from products")
    fun getAll(): List<Product>

    @Query("select * from products where `key`=:keyValue")
    fun getByKey(keyValue: Int): Product

    @Insert
    fun add(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)
}