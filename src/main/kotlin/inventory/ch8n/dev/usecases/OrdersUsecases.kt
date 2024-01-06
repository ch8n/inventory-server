package inventory.ch8n.dev.usecases

import inventory.ch8n.dev.data.database.OrderDB
import inventory.ch8n.dev.data.models.*
import kotlin.random.Random

class GetOrdersUsecase(private val orderDB: OrderDB) {
    fun all() = orderDB.getAll()
    fun getId(orderId: OrderId) = orderDB.getById(orderId)
}

class UpdateOrdersUsecase(
    private val orderDB: OrderDB,
    private val getCustomerUsecase: GetCustomerUsecase,
    private val getProductUsecase: GetProductUsecases,
    private val getOrdersUsecase: GetOrdersUsecase,
) {
    fun create(createOrderRequest: CreateOrderRequest): Order {
        val customerId = CustomerId(createOrderRequest.customerId)

        val customerFound = getCustomerUsecase.getId(customerId)
            ?: throw IllegalArgumentException("Customer not found ${customerId.value}!")

        val orderItems = createOrderRequest.products.map {
            val productId = ProductId(it.productId.value)
            val foundProduct = getProductUsecase.getId(productId)
                ?: throw IllegalArgumentException("Product not found ${customerId.value}!")
            foundProduct to it.quantity
        }.toMap()

        val order = Order(
            orderId = OrderId(value = Random.nextLong()),
            customer = customerFound.customerId,
            orderStatus = OrderStatus.Booked,
            products = orderItems.map { (product, qty) ->
                OrderItem(
                    productId = product.productId,
                    quantity = qty,
                    price = product.price
                )
            },
            orderDate = System.currentTimeMillis(),
            totalAmount = orderItems.entries.sumOf { (product, qty) ->
                product.price * qty
            }
        )

        orderDB.add(order)
        return order
    }

    fun update(updateOrderRequest: UpdateOrderRequest): Order {

        val customerId = CustomerId(updateOrderRequest.customerId)

        val customerFound = getCustomerUsecase.getId(customerId)
            ?: throw IllegalArgumentException("Customer not found ${customerId.value}!")

        val updatedItems = updateOrderRequest.products.map {
            val productId = ProductId(it.productId.value)
            val foundProduct = getProductUsecase.getId(productId)
                ?: throw IllegalArgumentException("Product not found ${customerId.value}!")
            Pair(productId, Pair(foundProduct.price, it.quantity))
        }.toMap()

        val existingOrder = getOrdersUsecase.getId(OrderId(updateOrderRequest.orderId))
            ?: throw IllegalArgumentException("Order not found with ${updateOrderRequest.orderId}!")

        val existingProducts = existingOrder.products.map {
            val productId = ProductId(it.productId.value)
            Pair(productId, Pair(it.price, it.quantity))
        }.toMap()

        val updatedExisting = existingProducts.map { (id, data) ->
            val (price, qty) = updatedItems.get(id) ?: return@map Pair(id, Pair(data.first, data.second))
            Pair(id, Pair(price, qty))
        }.toMap()

        val newItems = updatedItems
            .filter { (id, qty) -> updatedExisting.get(id) != null }
            .toMap()

        val finalItems = (updatedExisting + newItems).map { (id, data) ->
            OrderItem(
                productId = id,
                price = data.first,
                quantity = data.second
            )
        }
        val updated = existingOrder.copy(
            products = finalItems,
            totalAmount = finalItems.sumOf { it.quantity * it.price }
        )
        orderDB.replace(updated)
        return updated
    }

    fun remove(deleteOrderRequest: DeleteOrderRequest) {
        orderDB.remove(OrderId(deleteOrderRequest.orderId))
    }
}