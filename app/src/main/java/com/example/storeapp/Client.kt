package com.example.storeapp

class Client(cart:ArrayList<Product>? = arrayListOf(), name: String, password: String) : User(name, password), Shopping {
    override fun login(): Boolean {
        return super.login()
    }
//
//    override fun showInfo() {
//
//    }

    override fun buy() {
        TODO("Not yet implemented")
    }

    override fun getData() {
        TODO("Not yet implemented")
    }
}