package com.example.storeapp.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.viewModel.ProductListActivityViewModel
import com.example.storeapp.R
import com.example.storeapp.databinding.ActivityProductListBinding
import com.example.storeapp.model.entity.Product

class ProductListActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductListBinding
    lateinit var viewModel: ProductListActivityViewModel
    lateinit var adapter: ProductAdapter

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
        adapter = ProductAdapter(arrayListOf())
        binding.adapter = adapter

        loadProducts()

        adapter.onItemClickListener = {
            Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT).show()

            val intentDetail = Intent(applicationContext, ProductDetailActivity::class.java)
            intentDetail.putExtra("product_key", it.key)
            intentDetail.putExtra("product_id", it.id)

            startActivity(intentDetail)
        }

        adapter.onItemLongClickListener = {
            viewModel.deleteProduct(it).observe(this) { state ->
                if (state) {
                    Toast.makeText(
                        applicationContext,
                        "Producto ${it.name} eliminado...",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    Toast.makeText(
                        applicationContext,
                        "Error al eliminar producto ${it.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btAddProductListProduct.setOnClickListener {
            startActivity(Intent(applicationContext, ProductAddActivity::class.java))
        }


    }

    private fun loadProducts() {
//        viewModel.loadProducts()
        viewModel.products.observe(this) {
            if (it.isEmpty()) {
                viewModel.loadFakeData()
            }
            adapter.refresh(it as ArrayList<Product>)
        }
    }


    override fun onResume() {
        viewModel.loadProducts()
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