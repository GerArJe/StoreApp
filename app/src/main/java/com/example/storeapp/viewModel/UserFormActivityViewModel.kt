package com.example.storeapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storeapp.model.entity.User
import com.example.storeapp.model.repository.UserRepository

class UserFormActivityViewModel : ViewModel() {
    var user: User = User("", "", "", "", "")
    private val userRepository: UserRepository = UserRepository()

    fun singUp(): LiveData<User?> {
        return  userRepository.signUp(user, user.password)
    }
}