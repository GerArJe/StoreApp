package com.example.storeapp

import androidx.lifecycle.ViewModel

class ProductListActivityViewModel : ViewModel() {
    private val products: ArrayList<Product> = arrayListOf()
    var adapter: ProductAdapter = ProductAdapter(products)

    fun loadProducts() {
        products.apply {
            clear()
            add(Product("Monitor", 500000))
            add(Product("Teclado", 100000))
            add(Product("Disco duro", 250000))
            add(Product("Disco duro", 250000))
            add(Product("Disco duro", 250000))
            add(Product("Disco duro", 250000))
        }
    }

    fun refreshData() {
        adapter.refresh(products)
    }
}