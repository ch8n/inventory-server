package inventory.ch8n.dev.data.models

import kotlinx.serialization.Serializable
import java.io.File

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
    val imageUrl: List<String>,
    val category: Category,
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
data class DeleteProductRequest(
    val id: Long
)


data class UpdateProductVariantRequest(
    val productId: Long,
    val productIds: List<Long>
)

data class UpdateProductImagesRequest(
    val productId: Long,
    val images: File,
)


