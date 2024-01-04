package inventory.ch8n.dev.controllers

import inventory.ch8n.dev.data.models.*
import inventory.ch8n.dev.usecases.GetCustomerUsecase
import inventory.ch8n.dev.usecases.UpdateCustomerUsecases
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Routing.customerController() {
    route("v1/customer") {
        getCustomers()
        createCustomer()
        updateCustomer()
        removeCustomer()
    }
}


fun Route.getCustomers() {
    val getCustomerUsecase by inject<GetCustomerUsecase>()
    get {
        val parameters = call.request.queryParameters
        val id = parameters["id"]
        try {
            val customers = when {
                id == null -> getCustomerUsecase.all()
                else -> {
                    val longId = id.toLongOrNull() ?: throw IllegalArgumentException("Invalid id")
                    listOfNotNull(getCustomerUsecase.getId(CustomerId(longId)))
                }
            }
            call.respond(
                HttpStatusCode.OK,
                Response<List<Customer>>(data = customers)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                Response<List<Category>>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}

fun Route.createCustomer() {
    val updateCustomerUsecases by inject<UpdateCustomerUsecases>()
    post("/create") {
        try {
            val createCustomerRequest = call.receive<CreateCustomerRequest>()
            val customer = updateCustomerUsecases.create(createCustomerRequest)
            call.respond(
                HttpStatusCode.Created,
                Response<Customer>(data = customer)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                Response<Customer>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}

fun Route.updateCustomer() {
    val updateCustomerUsecases by inject<UpdateCustomerUsecases>()
    post("/update") {
        try {
            val customerRequest = call.receive<UpdateCustomerRequest>()
            val customer = updateCustomerUsecases.update(customerRequest)
            call.respond(
                HttpStatusCode.OK,
                Response<Customer>(data = customer)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                Response<Customer>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}

fun Route.removeCustomer() {
    val updateCustomerUsecases by inject<UpdateCustomerUsecases>()
    post("/remove") {
        try {
            val deleteCustomerRequest = call.receive<DeleteCustomerRequest>()
            updateCustomerUsecases.remove(deleteCustomerRequest)
            call.respond(
                HttpStatusCode.OK,
                Response<String>(data = "Deleted Customer $deleteCustomerRequest")
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                Response<String>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}




