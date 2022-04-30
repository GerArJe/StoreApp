package com.example.storeapp.model.entity

import com.example.storeapp.model.Shopping

class Client(cart:ArrayList<Product>? = arrayListOf(), name: String, password: String,
             document: String, email: String, photoUrl: String
) : User(name, password, document, email, photoUrl), Shopping {
//    override fun login(): Boolean {
//        return super.login()
//    }
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