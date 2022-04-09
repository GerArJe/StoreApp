package com.example.storeapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.storeapp.model.User

class UserFormActivityViewModel : ViewModel() {
    var user: User = User("", "", "", "", "")

    fun singUp(): Boolean {
        return user.name.trim().isNotEmpty() && user.password.trim()
            .isNotEmpty() && user.document.trim().isNotEmpty() && user.email.trim().isNotEmpty()
    }
}