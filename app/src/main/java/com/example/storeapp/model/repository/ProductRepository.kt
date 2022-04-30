package com.example.storeapp.model.repository

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storeapp.model.entity.Product
import com.example.storeapp.model.local.StoreAppDB
import com.example.storeapp.model.local.dao.ProductDAO
import com.example.storeapp.model.remote.StoreAppApi
import com.example.storeapp.model.remote.service.ProductService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import retrofit2.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductRepository(context: Context) {
    private val PRODUCT_COLLECTION: String = "products"
    private val db: StoreAppDB = StoreAppDB.getInstance(context)
    private val productDAO: ProductDAO = db.productDAO()

    var productsObserver: MutableLiveData<List<Product>> = MutableLiveData()
    var productObserver: MutableLiveData<Product> = MutableLiveData()

    private val firestore: FirebaseFirestore = Firebase.firestore

    private val api: Retrofit = StoreAppApi.getInstance()!!
    private val productService: ProductService = api.create(ProductService::class.java)

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

    fun listenAllFirestore() {
        firestore.collection(PRODUCT_COLLECTION).addSnapshotListener { snapshot, error ->
            snapshot?.let {
                val productList: ArrayList<Product> = arrayListOf<Product>()
                if (!snapshot.isEmpty) {
                    for (document in snapshot.documents) {
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
    }

    fun loadAllAPI() {
        productService.getAll().enqueue(object : Callback<Map<String, Product>?> {
            override fun onResponse(
                call: Call<Map<String, Product>?>,
                response: Response<Map<String, Product>?>
            ) {
                val productList: ArrayList<Product> = arrayListOf<Product>()
                response.body()?.let {
                    it.forEach { (id, product) ->
                        product.id = id
                        productList.add(product)
                    }
                }
                productsObserver.value = productList
            }

            override fun onFailure(call: Call<Map<String, Product>?>, t: Throwable) {
                println("Error ${t.message}")
            }

        })
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

    fun getByIdAPI(id: String) {
        productService.getById(id).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                response.body()?.let {
                    it.id = id
                    productObserver.value = it
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addLocal(product: Product) {
        productDAO.add(product)
    }

    fun addFirestore(product: Product, photoUri: Uri?): LiveData<String> {
        val productIdObserver: MutableLiveData<String> = MutableLiveData()
        photoUri?.let {
            val storageReference = Firebase.storage.reference.child(PRODUCT_COLLECTION)
            val time = SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(Date())
            val name = "${time}_${product.name}.jpg"
            storageReference.child(name).putFile(photoUri).addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { uri ->
                    product.urlImage = uri.toString()
                    firestore.collection(PRODUCT_COLLECTION).add(product)
                        .addOnSuccessListener { result ->
                            productIdObserver.value = result.id
                        }.addOnFailureListener {
                            productIdObserver.value = ""
                        }
                }
            }
        } ?: run {
            firestore.collection(PRODUCT_COLLECTION).add(product)
                .addOnSuccessListener { result ->
                    productIdObserver.value = result.id
                }.addOnFailureListener {
                    productIdObserver.value = ""
                }
        }

        return productIdObserver

    }

    fun addAPI(product: Product): MutableLiveData<String> {
        val productIdObserver: MutableLiveData<String> = MutableLiveData()
        productService.add(product).enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(
                call: Call<Map<String, String>>,
                response: Response<Map<String, String>>
            ) {
                response.body()?.let {
                    product.id = it["name"]!!
                    productIdObserver.value = product.id
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                productIdObserver.value = ""
            }

        })
        return productIdObserver
    }

    fun updateLocal(product: Product) {
        productDAO.update(product)
    }

    fun updateFirestore(product: Product): LiveData<Boolean> {
        val stateUpdateObserver: MutableLiveData<Boolean> = MutableLiveData()
        firestore.collection(PRODUCT_COLLECTION).document(product.id).set(product)
            .addOnSuccessListener {
                stateUpdateObserver.value = true
            }.addOnFailureListener {
                stateUpdateObserver.value = false
            }
        return stateUpdateObserver
    }

    fun updateAPI(product: Product): MutableLiveData<Boolean> {
        val stateUpdateObserver: MutableLiveData<Boolean> = MutableLiveData()
        productService.update(product.id, product).enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                response.body()?.let {
                    stateUpdateObserver.value = true
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                stateUpdateObserver.value = false
            }

        })
        return stateUpdateObserver
    }

    fun deleteLocal(product: Product) {
        productDAO.delete(product)
        loadAllLocal()
    }

    fun deleteFirestore(product: Product): LiveData<Boolean> {
        val stateDeleteObserver: MutableLiveData<Boolean> = MutableLiveData()
        firestore.collection(PRODUCT_COLLECTION).document(product.id).delete()
            .addOnSuccessListener {
                stateDeleteObserver.value = true
            }
            .addOnFailureListener {
                stateDeleteObserver.value = false
            }
        loadAllFirestore()
        return stateDeleteObserver
    }

    fun deleteAPI(product: Product): MutableLiveData<Boolean> {
        val stateUpdateObserver: MutableLiveData<Boolean> = MutableLiveData()
        productService.delete(product.id).enqueue(object : Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                response.body()?.let {
                    stateUpdateObserver.value = true
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                stateUpdateObserver.value = false
            }

        })
        return stateUpdateObserver
    }
}