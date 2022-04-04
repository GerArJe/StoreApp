package com.example.storeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductListBinding
    lateinit var viewModel: ProductListActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        viewModel = ViewModelProvider(this)[ProductListActivityViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.loadProducts()
        viewModel.refreshData()
    }
}