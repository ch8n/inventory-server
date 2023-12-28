package inventory.ch8n.dev.data.database

import inventory.ch8n.dev.data.models.Product
import inventory.ch8n.dev.data.models.ProductId

class ProductDB {

    private val products = mutableListOf<Product>()

    init {
        repeat(10) {
            products.add(
                Product(
                    productId = ProductId(value = it.toLong()),
                    name = "Lourdes Lee $it",
                    description = "fabulas $it",
                    price = 2.3 * it,
                    stockQuantity = 3912 * it,
                    imageUrl = "https://picsum.photos/200/300",
                    category = "per",
                    variants = emptyList()
                )
            )
        }
    }

    fun add(product: Product) {
        products.add(product)
    }

    fun replace(product: Product) {
        products.replaceAll {
            if (it.productId == product.productId) product else it
        }
    }

    fun remove(product: Product) {
        products.removeIf { it.productId == product.productId }
    }

    fun getAll() = products

    fun getById(productId: ProductId) = products.find { it.productId == productId }
}