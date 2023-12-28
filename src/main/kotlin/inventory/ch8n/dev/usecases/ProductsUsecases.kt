package inventory.ch8n.dev.usecases

import inventory.ch8n.dev.data.database.ProductDB
import inventory.ch8n.dev.data.models.CreateProductRequest
import inventory.ch8n.dev.data.models.Product
import inventory.ch8n.dev.data.models.ProductId
import inventory.ch8n.dev.data.models.UpdateProductRequest
import java.util.UUID
import kotlin.random.Random

class GetProduct(private val productDB: ProductDB) {
    fun all() = productDB.getAll()
    fun getId(productId: ProductId) = productDB.getById(productId)
}

class UpsertProduct(private val productDB: ProductDB) {
    fun create(createProductRequest: CreateProductRequest): Product {
        val product = Product(
            productId = ProductId(value = Random.nextLong()),
            name = createProductRequest.name,
            description = createProductRequest.description,
            price = createProductRequest.price,
            stockQuantity = createProductRequest.stockQuantity,
            imageUrl = emptyList(),
            category = createProductRequest.category,
            variants = listOf()
        )
        productDB.add(product)
        return product
    }

    fun update(updateProductRequest: UpdateProductRequest): Product {
        val founded = productDB.getById(ProductId(updateProductRequest.id))
            ?: throw IllegalStateException("Product not found by id ${updateProductRequest.id}")
        val updated = founded.copy(
            name = updateProductRequest.name ?: founded.name,
            description = updateProductRequest.description ?: founded.description,
            price = updateProductRequest.price ?: founded.price,
            stockQuantity = updateProductRequest.stockQuantity ?: founded.stockQuantity,
            category = updateProductRequest.category ?: founded.category,
        )
        productDB.replace(updated)
        return updated
    }

    fun remove(productId: ProductId) {
        productDB.remove(productId)
    }
}