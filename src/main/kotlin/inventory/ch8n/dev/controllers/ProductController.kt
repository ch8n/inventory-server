package inventory.ch8n.dev.controllers

import inventory.ch8n.dev.data.database.ProductDB
import inventory.ch8n.dev.data.models.*
import inventory.ch8n.dev.usecases.GetProduct
import inventory.ch8n.dev.usecases.UpsertProduct
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject


fun Routing.productController() {
    route("v1/products") {
        getProducts()
        createProduct()
        updateProduct()
        removeProduct()
    }
}

fun Route.createProduct() {
    val createProducts by inject<UpsertProduct>()
    post("/create") {
        try {
            val createProductRequest = call.receive<CreateProductRequest>()
            val product = createProducts.create(createProductRequest)
            call.respond(
                HttpStatusCode.Created,
                Response<Product>(data = product)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                Response<Product>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}

fun Route.updateProduct() {
    val createProducts by inject<UpsertProduct>()
    post("/update") {
        try {
            val createProductRequest = call.receive<UpdateProductRequest>()
            val product = createProducts.update(createProductRequest)
            call.respond(
                HttpStatusCode.Created,
                Response<Product>(data = product)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                Response<Product>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}

fun Route.removeProduct() {
    val createProducts by inject<UpsertProduct>()
    post("/remove") {
        try {
            val deleteProduct = call.receive<DeleteProductRequest>()
            createProducts.remove(ProductId(deleteProduct.id))
            call.respond(
                HttpStatusCode.OK,
                Response<String>(data = "Deleted Product ${deleteProduct.id}")
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

private fun Route.getProducts() {
    val getProducts by inject<GetProduct>()
    get {
        val parameters = call.request.queryParameters
        val id = parameters["id"]
        try {
            val products = when {
                id == null -> getProducts.all()
                else -> {
                    val longId = id.toLongOrNull() ?: throw IllegalArgumentException("Invalid id")
                    listOfNotNull(getProducts.getId(ProductId(longId)))
                }
            }
            call.respond(
                HttpStatusCode.OK,
                Response<List<Product>>(data = products)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                Response<List<Product>>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}


