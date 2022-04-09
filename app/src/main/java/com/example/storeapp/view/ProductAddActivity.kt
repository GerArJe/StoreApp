package com.example.storeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.R
import com.example.storeapp.databinding.ActivityProductAddBinding
import com.example.storeapp.model.entity.Product
import com.example.storeapp.viewModel.ProductAddActivityViewModel

class ProductAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductAddBinding
    private lateinit var viewModel: ProductAddActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val product: Product? = intent.getSerializableExtra("product") as Product?

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_add)


        viewModel = ViewModelProvider(this)[ProductAddActivityViewModel::class.java]

        binding.viewModel = viewModel

        product?.let {
            binding.tvTitleProductForm.text = "${getString(R.string.editar)} ${it.name}"
            viewModel.product = it
            binding.btAddProductForm.text = getString(R.string.editar_producto)
            binding.btAddProductForm.setOnClickListener {
                viewModel.edit()
                finish()
            }
        } ?: run {
            binding.btAddProductForm.setOnClickListener {
                viewModel.add()
                finish()
            }
        }


        binding.btReturnProductForm.setOnClickListener {
            finish()
        }


    }
}