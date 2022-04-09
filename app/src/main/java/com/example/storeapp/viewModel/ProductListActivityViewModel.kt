package com.example.storeapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storeapp.model.entity.Product
import com.example.storeapp.model.repository.ProductRepository
import com.example.storeapp.view.ProductAdapter

class ProductListActivityViewModel(application: Application) : AndroidViewModel(application) {
    //    var adapter: ProductAdapter = ProductAdapter(products)
    private val productRepository: ProductRepository = ProductRepository(application)
    var products: LiveData<List<Product>> = productRepository.products

    var productVariable: MutableLiveData<Product> = MutableLiveData()

    fun deleteProduct(product: Product) {
        productRepository.deleteLocal(product)
    }

    fun loadFakeData() {
        productRepository.loadFakeData()
    }

    fun loadProducts() {
        productRepository.loadAllLocal()
    }
}