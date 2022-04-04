package com.example.storeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.databinding.ActivityUserFormBinding

class UserFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserFormBinding
    private lateinit var viewModel: UserFormActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_form)
        viewModel = ViewModelProvider(this)[UserFormActivityViewModel::class.java]
        binding.viewModel = viewModel

        binding.btSingUpUserForm.setOnClickListener {
            if (viewModel.singUp()){
                Toast.makeText(this, "Registrando....", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show()
            }
        }

    }
}