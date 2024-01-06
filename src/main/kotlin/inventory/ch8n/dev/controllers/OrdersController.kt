package inventory.ch8n.dev.controllers

import inventory.ch8n.dev.data.models.*
import inventory.ch8n.dev.usecases.GetOrdersUsecase
import inventory.ch8n.dev.usecases.UpdateOrdersUsecase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Routing.ordersController() {
    route("v1/orders") {
        getOrders()
        createOrder()
        updateOrder()
        deleteOrder()
    }
}

fun Route.getOrders() {
    val getOrdersUsecase by inject<GetOrdersUsecase>()
    get {
        val parameters = call.request.queryParameters
        val id = parameters["id"]
        try {
            val orders = when {
                id == null -> getOrdersUsecase.all()
                else -> {
                    val longId = id.toLongOrNull() ?: throw IllegalArgumentException("Invalid id")
                    listOfNotNull(getOrdersUsecase.getId(OrderId(longId)))
                }
            }
            call.respond(
                HttpStatusCode.OK,
                Response<List<Order>>(data = orders)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                Response<List<Order>>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}

fun Route.createOrder() {
    val updateOrdersUsecase by inject<UpdateOrdersUsecase>()
    post("/create") {
        try {
            val createOrderRequest = call.receive<CreateOrderRequest>()
            val order = updateOrdersUsecase.create(createOrderRequest)
            call.respond(
                HttpStatusCode.Created,
                Response<Order>(data = order)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                Response<Order>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}

fun Route.updateOrder() {
    val updateOrdersUsecase by inject<UpdateOrdersUsecase>()
    post("/update") {
        try {
            val updateOrderRequest = call.receive<UpdateOrderRequest>()
            val order = updateOrdersUsecase.update(updateOrderRequest)
            call.respond(
                HttpStatusCode.OK,
                Response<Order>(data = order)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                Response<Order>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}

fun Route.deleteOrder() {
    val updateOrdersUsecase by inject<UpdateOrdersUsecase>()
    post("/remove") {
        try {
            val deleteCategoryRequest = call.receive<DeleteOrderRequest>()
            updateOrdersUsecase.remove(deleteCategoryRequest)
            call.respond(
                HttpStatusCode.OK,
                Response<String>(data = "Deleted Product $deleteCategoryRequest")
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




