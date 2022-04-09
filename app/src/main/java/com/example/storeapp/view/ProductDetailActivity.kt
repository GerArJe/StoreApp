package com.example.storeapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.R
import com.example.storeapp.databinding.ActivityProductDetailBinding
import com.example.storeapp.model.entity.Product
import com.example.storeapp.viewModel.ProductDetailActivityViewModel

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var viewModel: ProductDetailActivityViewModel
    private var productKey: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productKey = intent.getIntExtra("product_key", 0)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        viewModel = ViewModelProvider(this)[ProductDetailActivityViewModel::class.java]
        viewModel.getProductByKey(productKey)

        binding.product = Product(name = "", price = 0)

        viewModel.product.observe(this) {
            it?.let {
                binding.product = it
            }
        }

        binding.btEditProductDetail.setOnClickListener {
            val intentForm = Intent(applicationContext, ProductAddActivity::class.java)
            intentForm.putExtra("product", binding.product)
            startActivity(intentForm)
        }

        binding.btReturnProductDetail.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        viewModel.getProductByKey(productKey)
        super.onResume()

    }
}