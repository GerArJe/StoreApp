package com.example.storeapp.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storeapp.model.entity.Product
import com.example.storeapp.model.local.StoreAppDB
import com.example.storeapp.model.local.dao.ProductDAO

class ProductRepository(context: Context) {
    private val db: StoreAppDB = StoreAppDB.getInstance(context)
    private val productDAO: ProductDAO = db.productDAO()
    lateinit var products: LiveData<List<Product>>

    init {
        loadAllLocal()
    }

    fun loadAllLocal() {
        products = productDAO.getAll()
    }

    fun loadFakeData() {
        productDAO.apply {
            add(
                Product(
                    name = "Monitor",
                    price = 500000,
                    urlImage = "https://images.samsung.com/is/image/samsung/mx-t35f-lf24t350fhlxzx-frontblack-308825225?\$720_576_PNG\$"
                )
            )
            add(Product(name = "Teclado", price = 100000))
            add(Product(name = "Disco duro", price = 250000))
            add(Product(name = "Headset", price = 250000))
            add(Product(name = "Microfono", price = 250000))
            add(Product(name = "Camara", price = 250000))
        }
    }

    fun getByKeyLocal(key: Int): LiveData<Product> {
        return productDAO.getByKey(key)
    }

    fun addLocal(product: Product) {
        productDAO.add(product)
    }

    fun updateLocal(product: Product) {
        productDAO.update(product)
    }

    fun deleteLocal(product: Product) {
        productDAO.delete(product)
    }
}