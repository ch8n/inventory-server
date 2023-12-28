package inventory.ch8n.dev.controllers

import inventory.ch8n.dev.data.database.ProductDB
import inventory.ch8n.dev.data.models.Product
import inventory.ch8n.dev.data.models.ProductId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

@Serializable
data class ResponseError(
    val serverError: String,
    val clientError: String
)

@Serializable
data class Response<T>(
    val error: ResponseError? = null,
    val data: T? = null
)

fun Routing.productController() {
    route("v1/products") {
        val getProducts by inject<GetProducts>()
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
}


class GetProducts(private val productDB: ProductDB) {
    fun all() = productDB.getAll()
    fun getId(productId: ProductId) = productDB.getById(productId)
}