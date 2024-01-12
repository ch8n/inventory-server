package inventory.ch8n.dev.data.database

import inventory.ch8n.dev.data.models.CategoryId
import inventory.ch8n.dev.data.models.Product
import inventory.ch8n.dev.data.models.ProductId

class ProductDB {

    private val products = mutableListOf<Product>()

    init {
        repeat(3) {
            products.add(
                Product(
                    productId = ProductId(value = it.toLong()),
                    name = "Lourdes Lee $it",
                    description = "fabulas $it",
                    price = 2.3 * it,
                    stockQuantity = 3912 * it,
                    imageUrls = listOf("https://picsum.photos/200/300"),
                    categoryId = CategoryId(value = it.toLong()),
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
            if (it.productId.value == product.productId.value) product else it
        }
    }

    fun remove(productId: ProductId) {
        val updated = products.filter { it.productId.value != productId.value }
        products.clear()
        products.addAll(updated)
    }

    fun getAll() = products

    fun getById(productId: ProductId) = products.find { it.productId.value == productId.value }
    fun getByCategory(categoryId: CategoryId) = products.filter { it.categoryId.value == categoryId.value }
}