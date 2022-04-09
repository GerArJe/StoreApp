package com.example.storeapp.viewModel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storeapp.model.entity.Product
import com.example.storeapp.model.repository.ProductRepository

class ProductDetailActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository: ProductRepository = ProductRepository(application)

    lateinit var product: LiveData<Product>

    fun getProductByKey(productKey: Int) {
        product = productRepository.getByKeyLocal(productKey)
    }
}