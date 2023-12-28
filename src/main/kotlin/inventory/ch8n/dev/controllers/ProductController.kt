package inventory.ch8n.dev.controllers

import inventory.ch8n.dev.data.database.ProductDB
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.productController() {
    route("/products") {
        val getProducts by inject<GetProducts>()

        get {
            val products = getProducts.all()
            call.respond(products)
        }
    }
}


class GetProducts(private val productDB: ProductDB) {
    fun all() = productDB.getAll()
}