package inventory.ch8n.dev.data.models

import kotlinx.serialization.Serializable

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

@Serializable
data class Paged(
    val currentPage: Int,
    val totalPageSize: Int,
)

@Serializable
data class PagedResponse<T>(
    val error: ResponseError? = null,
    val data: T? = null,
    val paged: Paged
)