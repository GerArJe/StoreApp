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