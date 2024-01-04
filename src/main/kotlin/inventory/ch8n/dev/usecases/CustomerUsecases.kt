package inventory.ch8n.dev.usecases

import inventory.ch8n.dev.data.database.CategoryDB
import inventory.ch8n.dev.data.database.CustomerDB
import inventory.ch8n.dev.data.models.*
import kotlin.random.Random

class GetCustomerUsecase(private val customerDB: CustomerDB) {
    fun all() = customerDB.getAll()
    fun getId(customerId: CustomerId) = customerDB.getById(customerId)
}

class UpdateCustomerUsecases(
    private val customerDB: CustomerDB
) {
    fun create(createCustomer: CreateCustomerRequest): Customer {
        val found = customerDB.getAll().find {
            it.email == createCustomer.email || it.phoneNumber == createCustomer.phoneNumber
        }
        if (found != null) throw IllegalArgumentException("Account already exist with email or phone number!")
        val customer = Customer(
            customerId = CustomerId(value = Random.nextLong()),
            firstName = createCustomer.firstName,
            lastName = createCustomer.lastName,
            email = createCustomer.email,
            phoneNumber = createCustomer.phoneNumber,
            shippingAddress = null
        )
        customerDB.add(customer)
        return customer
    }

    fun update(updateCustomer: UpdateCustomerRequest): Customer {
        val found = customerDB.getById(CustomerId(updateCustomer.customerId))
            ?: throw IllegalArgumentException("Customer not found!")
        val updated = found.copy(
            firstName = updateCustomer.firstName ?: found.firstName,
            lastName = updateCustomer.lastName ?: found.lastName,
            email = updateCustomer.email ?: found.email,
            phoneNumber = updateCustomer.phoneNumber ?: found.phoneNumber,
            shippingAddress = found.shippingAddress
        )
        customerDB.replace(updated)
        return updated
    }

    fun remove(deleteCustomerRequest: DeleteCustomerRequest) {
        customerDB.remove(CustomerId(deleteCustomerRequest.customerId))
    }
}