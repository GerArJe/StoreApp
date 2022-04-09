package com.example.storeapp.model

open class User(
    var name: String,
    var password: String,
    var document: String,
    var email: String,
    var photoUrl: String?
) {
    open fun login(

    ): Boolean =
        name == "prueba@prueba.com" && password == "123456"

//    abstract fun showInfo()

}