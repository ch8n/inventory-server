package inventory.ch8n.dev.data.models


@JvmInline
value class CustomerId(val value: Long)

data class Customer(
    val customerId: CustomerId,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val shippingAddress: List<Address>
)

data class Address(
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val landmark: String,
)
