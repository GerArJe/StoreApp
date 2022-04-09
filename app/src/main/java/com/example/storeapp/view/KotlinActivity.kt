package com.example.storeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storeapp.R
import com.example.storeapp.model.Product

class KotlinActivity : AppCompatActivity() {

    lateinit var urlImage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name: String = "Sebastian"
        var height: Double = 1.87
        var age: Int = 34
        var admin: Boolean = true

        println("$name altura $height, edad $age administrador? $admin")
        admin = false
        println("$name altura $height, edad $age administrador? $admin")

        val email: String = getString(R.string.email_data)
        val emailInput = "prueba@prueba.com"

        val password: String = getString(R.string.password_data)
        val passwordInput = "123456"

        if (login(emailInput, password)) {
            println("Iniciando sesion")
        } else if (email != emailInput) {
            println("Correo invalido")
        } else {
            println("Password Invalido")
        }

        val typeUser = 1
        when (typeUser) {
            1 -> {
                println("Soy administrador")
            }
            in 2..4 -> {

                println("Soy cliente")
            }
            else -> {
                println("Soy invitado")
            }
        }

        val products: ArrayList<String> = arrayListOf("Monitor", "Mouse", "Teclado")
        println(products)
        products.add("Disco duro")
        val mouse = products[1]
        val teclado = products[2]
        products[2] = "USB"
        products.removeAt(1)
        println(products)

        products.forEach {
            println(it)
        }

        val monitor = mutableMapOf<String, Any>("name" to "Monitor")
        monitor["price"] = 70000000
        monitor["amount"] = 45
        monitor["description"] = "algo"
        println(monitor)
        monitor["price"] = 800000
        val monitorMap = monitor["name"]
        monitor.remove("amount")


        for (product: String in products) {
            println(product)
        }

        for (entry: Map.Entry<String, Any> in monitor) {
            println("Clave: ${entry.key} - Valor: ${entry.value}")
        }

//        for (i: Int in (products.size - 1) downTo 0) {
//            println(i)
//        }

        var x: Int = 0
        while (x < 3) {
            println(x)
            x++
        }

        var y: Int = 0
        do {
            y++
            println(y)
        } while (x < 3)


        var testNull: String? = "jhikjk";
        testNull = null
        val testSize: Int? = testNull?.length
//        val otherTextNull: Int = testNull!!.length


        testSize?.let {
            println(it)
        } ?: run {
            println("El texto nulo")
        }
        loadProducts()
        showProduct(name = "sdasd", price = 23)

        val portatil: Product = Product("Portatil ASUS", 3500000, "No hay")
        portatil.getShortInfo()

        val (n, p, d, s) = portatil

        for ((key, value) in monitor) {
            println("$key - $value")
        }

//        val myCLient: User = User(name = "prueba@prueba.com", password = "123456")
//        myCLient.login()
//
//        val myUser: User = User(name = "prueba@prueba.com", password = "123456")

//        if (myCLient is User) {
//            println("Es cliente")
//        }

        clickListener {
            println(it)
            true
        }

    }

    fun loadProducts(): Unit {


    }

    fun showProduct(name: String, price: Int): Unit {
        println("El producto")
    }

    fun login(
        email: String,
        password: String
    ): Boolean =
        email == getString(R.string.email_data) && password == getString(R.string.password_data)

    fun clickListener(
        click:
            (String) -> Boolean
    ) {
        click("Hola")
    }
}