package inventory.ch8n.dev.plugins

import inventory.ch8n.dev.controllers.helloController
import inventory.ch8n.dev.controllers.productController
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        helloController()
        productController()
    }
}




