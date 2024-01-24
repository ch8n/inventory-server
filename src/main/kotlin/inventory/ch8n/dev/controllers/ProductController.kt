package inventory.ch8n.dev.controllers

import inventory.ch8n.dev.data.models.*
import inventory.ch8n.dev.usecases.GetCategoriesUsecases
import inventory.ch8n.dev.usecases.GetProductUsecases
import inventory.ch8n.dev.usecases.UpdateProductUsecases
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject


fun Routing.productController() {
    route("v1/products") {
        getProducts()
        getByCategory()
        createProduct()
        updateProduct()
        removeProduct()
        addProductVariant()
        removeProductVariant()
        addProductImage()
        removeProductImage()
    }
}

fun Route.getProducts() {
    val getProductsUsecases by inject<GetProductUsecases>()
    get {
        val parameters = call.request.queryParameters
        val id = parameters["id"]
        val page = parameters["page"]?.toIntOrNull() ?: 0
        var totalPageCount = 0
        try {
            val products = when {
                id == null -> {
                    val chunks = getProductsUsecases.all().chunked(12)
                    totalPageCount = chunks.size
                    chunks.getOrNull(page) ?: emptyList()
                }

                else -> {
                    val longId = id.toLongOrNull() ?: throw IllegalArgumentException("Invalid id")
                    listOfNotNull(getProductsUsecases.getId(ProductId(longId)))
                }
            }

            call.respond(
                HttpStatusCode.OK,
                PagedResponse<List<Product>>(
                    data = products,
                    paged = Paged(currentPage = page, totalPageSize = totalPageCount)
                )
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                PagedResponse<List<Product>>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    ),
                    paged = Paged(currentPage = page, totalPageSize = 0)
                )
            )
        }
    }
}

fun Route.getByCategory() {
    val getProductsUsecases by inject<GetProductUsecases>()
    val getCategoryUsecases by inject<GetCategoriesUsecases>()
    get("/category") {
        val parameters = call.request.queryParameters
        val categoryIdLong =
            parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid format category id")
        val page = parameters["page"]?.toIntOrNull() ?: 0
        try {
            val categoryId = CategoryId(categoryIdLong)
            val category = getCategoryUsecases.getId(categoryId = categoryId)
                ?: throw IllegalArgumentException("Category not found!")
            val chunked = getProductsUsecases.getByCategory(categoryId).chunked(12)
            val products = chunked.getOrNull(page) ?: emptyList()
            call.respond(
                HttpStatusCode.OK,
                PagedResponse<List<Product>>(
                    data = products,
                    paged = Paged(currentPage = page, totalPageSize = chunked.size)
                )
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                PagedResponse<List<Product>>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    ),
                    paged = Paged(currentPage = page, totalPageSize = 0)
                )
            )
        }
    }
}

fun Route.createProduct() {
    val createProducts by inject<UpdateProductUsecases>()
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
    val createProducts by inject<UpdateProductUsecases>()
    post("/update") {
        try {
            val updateProductRequest = call.receive<UpdateProductRequest>()
            val product = createProducts.update(updateProductRequest)
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
    val createProducts by inject<UpdateProductUsecases>()
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

fun Route.addProductVariant() {
    val createProducts by inject<UpdateProductUsecases>()
    post("/variant/add") {
        try {
            val addProductVariantRequest = call.receive<AddProductVariantRequest>()
            val product = createProducts.addVariant(addProductVariantRequest)
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

fun Route.removeProductVariant() {
    val createProducts by inject<UpdateProductUsecases>()
    post("/variant/remove") {
        try {
            val removeProductVariantRequest = call.receive<RemoveProductVariantRequest>()
            val product = createProducts.removeVariant(removeProductVariantRequest)
            call.respond(
                HttpStatusCode.Accepted,
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


fun Route.addProductImage() {
    val createProducts by inject<UpdateProductUsecases>()
    post("/image/add") {
        try {
            val productIdString = call.request.queryParameters.getOrFail("product_id")?.toLongOrNull()
                ?: throw IllegalArgumentException("Wrong format id!")
            val productId = ProductId(productIdString)
            val uploadedFileMultipart = call.receiveMultipart()
            val product = createProducts.uploadFile(uploadedFileMultipart, productId)
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

fun Route.removeProductImage() {
    val createProducts by inject<UpdateProductUsecases>()
    post("/image/remove") {
        val deleteProductImage = call.receive<RemoveProductImageRequest>()
        try {
            val product = createProducts.deleteImageOfProduct(deleteProductImage)
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






