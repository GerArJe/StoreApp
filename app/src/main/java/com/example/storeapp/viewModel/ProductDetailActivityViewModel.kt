package com.example.storeapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.storeapp.model.entity.Product
import com.example.storeapp.model.repository.ProductRepository

class ProductDetailActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository: ProductRepository = ProductRepository(application)

    lateinit var product: LiveData<Product>

    fun getProductByKey(productKey: Int) {
        productRepository.getByKeyLocal(productKey)
        product = productRepository.productObserver
    }

    fun getProductById(myProductId: String) {
        productRepository.getByIdFirestore(myProductId)
        product = productRepository.productObserver
    }
}