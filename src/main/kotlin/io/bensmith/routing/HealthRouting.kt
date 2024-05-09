package io.bensmith.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.healthRouting() {

    get("/health") {
        call.respondText("Healthy")
    }
}