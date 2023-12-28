package inventory.ch8n.dev.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.helloController() {
    get("/") {
        call.respondText("Hello World!")
    }
}