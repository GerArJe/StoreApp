package com.example.storeapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.storeapp.model.entity.Product
import com.example.storeapp.model.repository.ProductRepository

class ProductAddActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepository: ProductRepository = ProductRepository(application)
    var product = Product(name = "", price = 0)

    fun add() {
        productRepository.addLocal(product)
    }

    fun edit(){
        productRepository.updateLocal(product)
    }
}