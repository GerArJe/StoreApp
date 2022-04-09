package com.example.storeapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.storeapp.model.User

class MainActivityViewModel: ViewModel() {
    var user: User = User("", "", "", "", "")

    fun login(): Boolean{
        return user.email == "prueba@prueba.com" && user.password == "123456"
    }


}