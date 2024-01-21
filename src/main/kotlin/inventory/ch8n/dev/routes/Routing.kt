package inventory.ch8n.dev.routes

import inventory.ch8n.dev.controllers.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        categoryController()
        customerController()
        productController()
        ordersController()
        offerController()
    }
}




