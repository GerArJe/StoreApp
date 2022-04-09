package com.example.storeapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.storeapp.model.Product
import com.example.storeapp.view.ProductAdapter

class ProductListActivityViewModel : ViewModel() {
    private val products: ArrayList<Product> = arrayListOf()
    var adapter: ProductAdapter = ProductAdapter(products)

    fun loadProducts() {
        products.apply {
            clear()
            add(
                Product(
                    "Monitor",
                    500000,
                    "https://images.samsung.com/is/image/samsung/mx-t35f-lf24t350fhlxzx-frontblack-308825225?\$720_576_PNG\$"
                )
            )
            add(Product("Teclado", 100000))
            add(Product("Disco duro", 250000))
            add(Product("Headset", 250000))
            add(Product("Microfono", 250000))
            add(Product("Camara", 250000))
        }
    }

    fun refreshData() {
        adapter.refresh(products)
    }
}