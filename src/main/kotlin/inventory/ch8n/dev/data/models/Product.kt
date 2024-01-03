package inventory.ch8n.dev.data.models

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class ProductId(val value: Long)

@Serializable
data class Product(
    val productId: ProductId,
    val name: String,
    val description: String,
    val price: Double,
    val stockQuantity: Int,
    val imageUrls: List<String>,
    val categoryId: CategoryId,
    val variants: List<ProductId>
)

@Serializable
data class CreateProductRequest(
    val name: String,
    val description: String,
    val price: Double,
    val stockQuantity: Int,
    val categoryId: Long,
)

@Serializable
data class UpdateProductRequest(
    val id: Long,
    val name: String?,
    val description: String?,
    val price: Double?,
    val stockQuantity: Int?,
    val categoryId: Long?,
)

@Serializable
data class DeleteProductRequest(val id: Long)


@Serializable
data class AddProductVariantRequest(
    val productId: Long,
    val variantProductId: Long
)

@Serializable
data class RemoveProductVariantRequest(
    val productId: Long,
    val variantProductId: Long
)

@Serializable
data class RemoveProductImageRequest(
    val productId: Long,
    val imageUrl: String
)


