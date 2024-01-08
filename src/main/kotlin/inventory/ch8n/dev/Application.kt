package inventory.ch8n.dev

import inventory.ch8n.dev.plugins.*
import inventory.ch8n.dev.routes.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main() {
    val server = embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    )

    server.start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configCors()
    koinDependencyInjection()
    configureRouting()
}
