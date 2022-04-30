package com.example.storeapp.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.R
import com.example.storeapp.viewModel.UserFormActivityViewModel
import com.example.storeapp.databinding.ActivityUserFormBinding
import com.example.storeapp.model.entity.User

class UserFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserFormBinding
    private lateinit var viewModel: UserFormActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_form)
        viewModel = ViewModelProvider(this)[UserFormActivityViewModel::class.java]
        binding.viewModel = viewModel

        binding.btSingUpUserForm.setOnClickListener {
            viewModel.singUp().observe(this) {
                it?.let {
                    print("SingUp")
                    login(it)
                }?:run {
                    Toast.makeText(applicationContext, getString(R.string.error_sign_up), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btReturnUserForm.setOnClickListener {
            finish()
        }

    }

    private fun login(user: User) {
        val preferences: SharedPreferences =
            getSharedPreferences("store_app.pref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean("login", true)
        editor.apply()

        val intentLogin = Intent(applicationContext, ProductListActivity::class.java)
        intentLogin.apply {
            putExtra("message", "Hola")
            putExtra("data", user.email)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intentLogin)
        Toast.makeText(this, "Inciando Sesion....", Toast.LENGTH_SHORT).show()
    }
}