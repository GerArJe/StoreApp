package com.example.storeapp

import androidx.lifecycle.ViewModel

class UserListActivityViewMoldel: ViewModel() {
    private val users: ArrayList<User> = arrayListOf()
    var adapter: UserAdapter = UserAdapter(users)

    fun loadUsers() {
        users.apply {
            clear()
            add(User("prueba1", "123456"))
            add(User("prueba2", "123456"))
            add(User("prueba3", "123456"))
            add(User("prueba4", "123456"))
            add(User("prueba5", "123456"))
            add(User("prueba6", "123456"))
        }
    }

    fun refreshData() {
        adapter.refresh(users)
    }

}