package com.example.storeapp

import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    var user: User = User("", "", "", "", "")

    fun login(): Boolean{
        return user.name == "prueba@prueba.com" && user.password == "123456"
    }


}