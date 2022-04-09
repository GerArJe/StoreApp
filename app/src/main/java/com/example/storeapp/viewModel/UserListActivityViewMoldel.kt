package com.example.storeapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.storeapp.model.entity.User
import com.example.storeapp.view.UserAdapter

class UserListActivityViewMoldel : ViewModel() {
    private val users: ArrayList<User> = arrayListOf()
    var adapter: UserAdapter = UserAdapter(users)

    fun loadUsers() {
        users.apply {
            clear()
            add(User("prueba1", "123456", "123456789", "prueba1@gmail.com", ""))
            add(User("prueba2", "123456", "123456789", "prueba2@gmail.com", ""))
            add(User("prueba3", "123456", "123456789", "prueba3@gmail.com", ""))
            add(User("prueba4", "123456", "123456789", "prueba4@gmail.com", ""))
        }
    }

    fun refreshData() {
        adapter.refresh(users)
    }

}