package com.example.storeapp.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.viewModel.MainActivityViewModel
import com.example.storeapp.R
import com.example.storeapp.databinding.ActivityMainBinding
import com.example.storeapp.model.entity.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: User
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences =
            getSharedPreferences("store_app.pref", MODE_PRIVATE)

//        preferences.edit().clear().apply()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        if (preferences.getBoolean("login", false)) {
            val intentLogin = Intent(applicationContext, ProductListActivity::class.java)
            intentLogin.apply {
                putExtra("message", "Hola")
                putExtra("data", viewModel.user.email)
            }
            startActivity(intentLogin)
            finish()
        }

        binding.tvTitleLogin.text = "Modificado por código"

        binding.viewModel = viewModel

        binding.title = "Modificado otra vez"


        binding.btSignupLogin.setOnClickListener {
            val intentSignup = Intent(applicationContext, UserFormActivity::class.java)
            startActivity(intentSignup)
        }
        binding.btLoginLogin.setOnClickListener {
            if (viewModel.login()) {

//                val preferences: SharedPreferences =
//                    getSharedPreferences("store_app.pref", MODE_PRIVATE)
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putBoolean("login", true)
                editor.apply()

                val intentLogin = Intent(applicationContext, ProductListActivity::class.java)
                intentLogin.apply {
                    putExtra("message", "Hola")
                    putExtra("data", viewModel.user.email)
                }
                startActivity(intentLogin)
                Toast.makeText(this, "Inciando Sesion....", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }


}