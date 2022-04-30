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
    var products: LiveData<List<Product>> = productRepository.productsObserver

    fun deleteProduct(product: Product): LiveData<Boolean> {
//        productRepository.deleteLocal(product)
        return productRepository.deleteFirestore(product)
    }

    fun loadFakeData() {
        productRepository.loadFakeData()
    }

    fun loadProducts() {
//        productRepository.loadAllLocal()
        productRepository.loadAllFirestore()
//        productRepository.listenAllFirestore()
//        productRepository.loadAllAPI()
    }
}