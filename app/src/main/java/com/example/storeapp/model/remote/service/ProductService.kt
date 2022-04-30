package com.example.storeapp.model.remote.service

import com.example.storeapp.model.entity.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductService {

    @GET("products.json")
    fun getAll(): Call<Map<String, Product>>

    @GET("products/{pid}.json")
    fun getById(@Path("pid") id: String): Call<Product>

    @POST("products.json")
    fun add(@Body product: Product): Call<Map<String,String>>

    @PUT("products/{pid}.json")
    fun update(@Path("pid") id: String, @Body product: Product): Call<Product>

    @DELETE("products/{pid}.json")
    fun delete(@Path("pid") id: String): Call<Unit>
}