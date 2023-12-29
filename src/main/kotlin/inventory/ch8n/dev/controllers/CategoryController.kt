package inventory.ch8n.dev.controllers

import inventory.ch8n.dev.data.models.*
import inventory.ch8n.dev.usecases.GetCategoriesUsecases
import inventory.ch8n.dev.usecases.UpdateCategoriesUsecases
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Routing.categoryController() {
    route("v1/categories") {
        getCategories()
        createCategory()
        updateCategory()
        removeCategory()
    }
}

private fun Route.getCategories() {
    val getCategoriesUsecases by inject<GetCategoriesUsecases>()
    get {
        val parameters = call.request.queryParameters
        val id = parameters["id"]
        try {
            val categories = when {
                id == null -> getCategoriesUsecases.all()
                else -> {
                    val longId = id.toLongOrNull() ?: throw IllegalArgumentException("Invalid id")
                    listOfNotNull(getCategoriesUsecases.getId(CategoryId(longId)))
                }
            }
            call.respond(
                HttpStatusCode.OK,
                Response<List<Category>>(data = categories)
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

private fun Route.createCategory() {
    val updateCategoriesUsecases by inject<UpdateCategoriesUsecases>()
    post("/create") {
        try {
            val createCategoryRequest = call.receive<CreateCategoryRequest>()
            val category = updateCategoriesUsecases.create(createCategoryRequest)
            call.respond(
                HttpStatusCode.Created,
                Response<Category>(data = category)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                Response<Category>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}

private fun Route.updateCategory() {
    val updateCategoriesUsecases by inject<UpdateCategoriesUsecases>()
    post("/update") {
        try {
            val updateCategoryRequest = call.receive<UpdateCategoryRequest>()
            val category = updateCategoriesUsecases.update(updateCategoryRequest)
            call.respond(
                HttpStatusCode.OK,
                Response<Category>(data = category)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                Response<Category>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}
private fun Route.removeCategory() {
    val updateCategoriesUsecases by inject<UpdateCategoriesUsecases>()
    post("/remove") {
        try {
            val deleteCategoryRequest = call.receive<DeleteCategoryRequest>()
            updateCategoriesUsecases.remove(deleteCategoryRequest)
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




