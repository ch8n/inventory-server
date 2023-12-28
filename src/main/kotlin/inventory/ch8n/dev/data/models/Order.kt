package inventory.ch8n.dev.data.models

@JvmInline
value class OrderId(val value: Long)


data class Order(
    val orderId: OrderId,
    val customer: Customer,
    val products: List<OrderItem>,
    val orderDate: Long,
    val totalAmount: Double
)

data class OrderItem(
    val product: Product,
    val quantity: Int
)