package com.example.storeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.R
import com.example.storeapp.databinding.ActivityProductDetailBinding
import com.example.storeapp.model.Product
import com.example.storeapp.viewModel.ProductDetailActivityViewModel

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var viewModel: ProductDetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val product: Product = intent.getSerializableExtra("product") as Product
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        viewModel = ViewModelProvider(this)[ProductDetailActivityViewModel::class.java]
        viewModel.product = product
        binding.product = viewModel.product
    }
}