package inventory.ch8n.dev.data.models

import kotlinx.serialization.Serializable

enum class OrderStatus {
    None,
    Booked,
    Paid,
    Shipped,
    Issue
}


@JvmInline
@Serializable
value class OrderId(val value: Long)

@Serializable
data class Order(
    val orderId: OrderId,
    val customer: CustomerId,
    val orderStatus: OrderStatus,
    val products: List<OrderItem>,
    val orderDate: Long,
    val totalAmount: Double
)

@Serializable
data class OrderItem(
    val productId: ProductId,
    val quantity: Int,
    val price: Double,
)

@Serializable
data class OrderItemRequest(
    val productId: ProductId,
    val quantity: Int,
)

@Serializable
data class CreateOrderRequest(
    val customerId: Long,
    val products: List<OrderItemRequest>,
)

@Serializable
data class UpdateOrderRequest(
    val orderId: Long,
    val customerId: Long,
    val products: List<OrderItemRequest>,
)

@Serializable
data class DeleteOrderRequest(
    val orderId: Long,
)