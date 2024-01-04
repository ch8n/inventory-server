package inventory.ch8n.dev.data.database

import inventory.ch8n.dev.data.models.*

class CustomerDB {

    private val customers = mutableListOf<Customer>()

    init {
        customers.add(
            Customer(
                customerId = CustomerId(value = 2671),
                firstName = "Vance O'Neill",
                lastName = "Antoine Goodman",
                email = "flora.oneil@example.com",
                phoneNumber = "(237) 257-7834",
                shippingAddress = Address(
                    street = "altera",
                    city = "West End",
                    state = "Illinois",
                    zipCode = "30948",
                    landmark = "mazim"
                )
            )
        )

        customers.add(
            Customer(
                customerId = CustomerId(value = 2404),
                firstName = "Eloise Gilmore",
                lastName = "Lana Leblanc",
                email = "flossie.walter@example.com",
                phoneNumber = "(236) 762-7820",
                shippingAddress = Address(
                    street = "ullamcorper",
                    city = "Ambler",
                    state = "Illinois",
                    zipCode = "34078",
                    landmark = "persecuti"
                )
            )
        )
    }

    fun add(customer: Customer) {
        customers.add(customer)
    }

    fun replace(customer: Customer) {
        customers.replaceAll {
            if (it.customerId.value == customer.customerId.value) {
                customer
            } else {
                it
            }
        }
    }

    fun remove(customerId: CustomerId) {
        customers.removeIf { it.customerId.value == customerId.value }
    }

    fun getAll() = customers

    fun getById(customerId: CustomerId) = customers.find { it.customerId.value == customerId.value }
}