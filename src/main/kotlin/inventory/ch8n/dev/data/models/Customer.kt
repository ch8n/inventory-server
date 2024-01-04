package inventory.ch8n.dev.data.models

import kotlinx.serialization.Serializable


@JvmInline
@Serializable
value class CustomerId(val value: Long)

@Serializable
data class Customer(
    val customerId: CustomerId,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val shippingAddress: Address?
)

@Serializable
data class Address(
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val landmark: String,
)

@Serializable
data class CreateCustomerRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String
)

@Serializable
data class UpdateCustomerRequest(
    val customerId: Long,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val phoneNumber: String?
)

@Serializable
data class DeleteCustomerRequest(val customerId: Long)

@Serializable
data class UpdateAddressRequest(
    val customerId: Long,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val landmark: String,
)

