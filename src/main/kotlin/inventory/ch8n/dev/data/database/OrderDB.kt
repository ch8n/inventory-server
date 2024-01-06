package inventory.ch8n.dev.data.database

import inventory.ch8n.dev.data.models.*
import kotlin.random.Random

class OrderDB {

    private val orders = mutableListOf<Order>()

    init {
        repeat(3) {
            orders.add(
                Order(
                    orderId = OrderId(value = Random.nextLong()),
                    customer = CustomerId(value = 3764),
                    products = listOf(
                        OrderItem(
                            productId = ProductId(value = 0),
                            quantity = 8094,
                            price = 123.0
                        ),
                        OrderItem(
                            productId = ProductId(value = 1),
                            quantity = 8094,
                            price = 231.0
                        ),
                        OrderItem(
                            productId = ProductId(value = 2),
                            quantity = 8094,
                            price = 444.0
                        )
                    ),
                    orderDate = 9050,
                    totalAmount = 2.3,
                    orderStatus = OrderStatus.Booked
                )
            )
        }
    }

    fun add(order: Order) {
        orders.add(order)
    }

    fun replace(updateOrder: Order) {
        this.orders.replaceAll {
            if (it.orderId.value == updateOrder.orderId.value) {
                updateOrder
            } else {
                it
            }
        }
    }

    fun remove(orderId: OrderId) {
        orders.removeIf { it.orderId.value == orderId.value }
    }

    fun getAll() = orders

    fun getById(orderId: OrderId) = orders.find { it.orderId.value == orderId.value }
}