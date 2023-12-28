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
    val imageUrl: String,
    val category: String,
    val variants: List<ProductId>
)


