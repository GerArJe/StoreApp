package com.example.storeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.R
import com.example.storeapp.viewModel.UserListActivityViewMoldel
import com.example.storeapp.databinding.ActivityUserListBinding

class UserListActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserListBinding
    lateinit var viewModel: UserListActivityViewMoldel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list)
        viewModel = ViewModelProvider(this)[UserListActivityViewMoldel::class.java]
        binding.viewModel = viewModel
        viewModel.loadUsers()
        viewModel.refreshData()
    }
}