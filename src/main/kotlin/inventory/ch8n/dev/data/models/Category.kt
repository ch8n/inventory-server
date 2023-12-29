package inventory.ch8n.dev.data.models

import kotlinx.serialization.Serializable


@JvmInline
@Serializable
value class CategoryId(val value: Long)

@Serializable
data class Category(
    val categoryId: CategoryId,
    val name: String
)

@Serializable
data class CreateCategoryRequest(
    val name: String
)
@Serializable
data class UpdateCategoryRequest(
    val categoryId: Long,
    val name: String
)

@Serializable
data class DeleteCategoryRequest(
    val categoryId: Long
)
