package com.example.storeapp.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private var myProductId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productKey = intent.getIntExtra("product_key", 0)
        myProductId = intent.getStringExtra("product_id")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        viewModel = ViewModelProvider(this)[ProductDetailActivityViewModel::class.java]
//        viewModel.getProductByKey(productKey)
        myProductId?.let {
            viewModel.getProductById(it)
        }

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
//        viewModel.getProductByKey(productKey)
        myProductId?.let {
            viewModel.getProductById(it)
        }
        super.onResume()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_logout -> {
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        val preferences: SharedPreferences = getSharedPreferences("store_app.pref", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}