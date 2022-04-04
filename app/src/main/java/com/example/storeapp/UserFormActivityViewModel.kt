package com.example.storeapp

import androidx.lifecycle.ViewModel

class UserFormActivityViewModel : ViewModel() {
    var user: User = User("", "")

    fun singUp(): Boolean {
        return user.name.trim().isNotEmpty() && user.password.trim().isNotEmpty()
    }
}