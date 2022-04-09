package com.example.storeapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.viewModel.ProductListActivityViewModel
import com.example.storeapp.R
import com.example.storeapp.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductListBinding
    lateinit var viewModel: ProductListActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle: Bundle? = intent.extras
        val message: String? = bundle?.getString("message")
        val data: String? = bundle?.getString("data")

        if (message != null && data != null) {
            title = "$message $data"
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        viewModel = ViewModelProvider(this)[ProductListActivityViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.loadProducts()
        viewModel.refreshData()

        viewModel.adapter.onItemClickListener = {
            Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT).show()

            val intentDetail = Intent(applicationContext, ProductDetailActivity::class.java)
            intentDetail.putExtra("product", it)

            startActivity(intentDetail)
        }
    }
}