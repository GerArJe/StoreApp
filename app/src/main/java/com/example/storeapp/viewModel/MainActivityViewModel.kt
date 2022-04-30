package com.example.storeapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storeapp.model.entity.User
import com.example.storeapp.model.repository.UserRepository

class MainActivityViewModel: ViewModel() {
    var user: User = User("", "", "", "", "")
    private val userRepository: UserRepository = UserRepository()

    fun login(): LiveData<User?> {
        return userRepository.login(user.email, user.password)
    }


}