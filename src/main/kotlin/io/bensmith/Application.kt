package io.bensmith

import io.bensmith.modules.applicationModule
import io.bensmith.routing.customerRouting
import io.bensmith.routing.healthRouting
import io.bensmith.services.CustomerService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.KoinIsolated

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.main() {

    // Dependency Injection
    install(KoinIsolated) {
        modules(applicationModule(environment.config))
    }

    // Serialization
    install(ContentNegotiation) {
        json()
    }

    // Routing
    val customerService by inject<CustomerService>()

    routing {
        customerRouting(customerService)
        healthRouting()
    }
}
