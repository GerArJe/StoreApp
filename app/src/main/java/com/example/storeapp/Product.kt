package com.example.storeapp

data class Product(
    var name: String,
    val price: Int,
    var description: String = "",
    val status: ProductStatus = ProductStatus.AVAILABLE
) {
    init {
        println("Producto creado: $name - $$price")

    }

    fun getShortInfo(): String = "$name - $$price"

    override fun toString(): String {
        return "Product(name='$name', price=$price)"
    }

}