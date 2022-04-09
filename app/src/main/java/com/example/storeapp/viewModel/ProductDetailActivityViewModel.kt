package com.example.storeapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.storeapp.model.Product

class ProductDetailActivityViewModel : ViewModel() {

    var product = Product("", 0)
}