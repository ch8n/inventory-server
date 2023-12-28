package inventory.ch8n.dev.data.models

@JvmInline
value class ProductId(val value: Long)

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


