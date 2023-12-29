package inventory.ch8n.dev.plugins

import inventory.ch8n.dev.controllers.categoryController
import inventory.ch8n.dev.controllers.productController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        categoryController()
        productController()
    }
}




