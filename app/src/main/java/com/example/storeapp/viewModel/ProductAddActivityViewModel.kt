package com.example.storeapp.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.storeapp.model.entity.Product
import com.example.storeapp.model.repository.ProductRepository

class ProductAddActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepository: ProductRepository = ProductRepository(application)
    var product = Product(name = "", price = 0)

    fun add(photoUri: Uri?): LiveData<String> {
//        productRepository.addLocal(product)
        return productRepository.addFirestore(product, photoUri)
    }

    fun edit(): LiveData<Boolean> {
//        productRepository.updateLocal(product)
        return productRepository.updateFirestore(product)
    }
}