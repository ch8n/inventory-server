package inventory.ch8n.dev.routes

import inventory.ch8n.dev.controllers.categoryController
import inventory.ch8n.dev.controllers.customerController
import inventory.ch8n.dev.controllers.ordersController
import inventory.ch8n.dev.controllers.productController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        categoryController()
        customerController()
        productController()
        ordersController()
    }
}




