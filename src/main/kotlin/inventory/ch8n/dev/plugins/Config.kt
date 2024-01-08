package inventory.ch8n.dev.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*


fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.configCors() {
    install(CORS) {
        anyHost()
    }
}








