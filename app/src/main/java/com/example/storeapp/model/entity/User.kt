package com.example.storeapp.model.entity

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

open class User(
    @JvmField @Exclude
    var id: String = "",
    var document: String = "",
    var name: String = "",
    var email: String = "",
    @JvmField @Exclude
    var password: String = "",
    @JvmField @PropertyName("url_photo")
    var photoUrl: String = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSBWgY0gJdZscKV-5b6lKNIJhsprtz1jwymWA&usqp=CAU"
) {
//    open fun login(
//
//    ): Boolean =
//        name == "prueba@prueba.com" && password == "123456"

//    abstract fun showInfo()

}