package io.bensmith.routing

import io.bensmith.models.CustomerToCreate
import io.bensmith.services.CustomerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting(customerService: CustomerService) {

    get("/api/customer") {
        val customers = customerService.getAll()
        call.respond(customers)
    }

    get("/api/customer/{id?}") {
        val id = call.parameters["id"]?.toIntOrNull()

        if (id == null) {
            return@get call.respond(HttpStatusCode.BadRequest, "Missing id")
        }

        val customer = customerService.getById(id)

        if (customer == null) {
            return@get call.respond(HttpStatusCode.NotFound, "No customer with id: $id")
        }

        call.respond(customer)
    }

    post("/api/customer") {
        val customerToCreate = call.receive<CustomerToCreate>()

        val customer = customerService.create(customerToCreate)

        call.respond(HttpStatusCode.Created, customer)
    }

    delete("/api/customer/{id?}") {
        val id = call.parameters["id"]?.toIntOrNull()

        if (id == null) {
            return@delete call.respond(HttpStatusCode.BadRequest, "Missing id")
        }

        val success = customerService.delete(id)

        if (!success) {
            return@delete call.respond(HttpStatusCode.NotFound, "No customer with id: $id")
        }

        call.respond(HttpStatusCode.Accepted, "Customer deleted")
    }
}