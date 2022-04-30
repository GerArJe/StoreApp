package com.example.storeapp.model.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.storeapp.model.entity.Product
import com.example.storeapp.model.local.StoreAppDB
import com.example.storeapp.model.local.dao.ProductDAO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductRepository(context: Context) {
    private val PRODUCT_COLLECTION: String = "products"
    private val db: StoreAppDB = StoreAppDB.getInstance(context)
    private val productDAO: ProductDAO = db.productDAO()
    var productsObserver: MutableLiveData<List<Product>> = MutableLiveData()
    var productObserver: MutableLiveData<Product> = MutableLiveData()
    private val firestore: FirebaseFirestore = Firebase.firestore

    fun loadAllLocal() {
        val productsList = productDAO.getAll()
        productsObserver.value = productsList
    }

    fun loadAllFirestore() {
        firestore.collection(PRODUCT_COLLECTION).get().addOnSuccessListener {
            val productList: ArrayList<Product> = arrayListOf<Product>()
            if (!it.isEmpty) {
                for (document in it.documents) {
                    val myProduct: Product? = document.toObject(Product::class.java)
                    myProduct?.let {
                        it.id = document.id
                        productList.add(it)
                    }
                }
            }
            productsObserver.value = productList
        }
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

    fun getByKeyLocal(key: Int) {
        productObserver.value = productDAO.getByKey(key)
    }

    fun getByIdFirestore(id: String) {
        firestore.collection(PRODUCT_COLLECTION).document(id).get().addOnSuccessListener {
            val myProduct: Product? = it.toObject(Product::class.java)
            myProduct?.let {
                it.id = id
                productObserver.value = it
            }
        }
    }

    fun addLocal(product: Product) {
        productDAO.add(product)
    }

    fun addFirestore(product: Product) {
        firestore.collection(PRODUCT_COLLECTION).add(product)
    }

    fun updateLocal(product: Product) {
        productDAO.update(product)
    }

    fun updateFirestore(product: Product) {
        firestore.collection(PRODUCT_COLLECTION).document(product.id).set(product)
    }

    fun deleteLocal(product: Product) {
        productDAO.delete(product)
        loadAllLocal()
    }

    fun deleteFirestore(product: Product) {
        firestore.collection(PRODUCT_COLLECTION).document(product.id).delete()
        loadAllFirestore()
    }
}