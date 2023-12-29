package inventory.ch8n.dev.usecases

import inventory.ch8n.dev.data.database.CategoryDB
import inventory.ch8n.dev.data.database.ProductDB
import inventory.ch8n.dev.data.models.*
import kotlin.random.Random

class GetProductUsecases(private val productDB: ProductDB) {
    fun all() = productDB.getAll()
    fun getId(productId: ProductId) = productDB.getById(productId)
}

class UpdateProductUsecases(
    private val productDB: ProductDB,
    private val categoryDB: CategoryDB,
) {
    fun create(createProductRequest: CreateProductRequest): Product {
        val category = categoryDB
            .getById(CategoryId(createProductRequest.categoryId))
            ?: throw IllegalArgumentException("Category not found!")
        val product = Product(
            productId = ProductId(value = Random.nextLong()),
            name = createProductRequest.name,
            description = createProductRequest.description,
            price = createProductRequest.price,
            stockQuantity = createProductRequest.stockQuantity,
            imageUrl = emptyList(),
            categoryId = category.categoryId,
            variants = listOf()
        )
        productDB.add(product)
        return product
    }

    fun update(updateProductRequest: UpdateProductRequest): Product {
        val foundProduct = productDB.getById(ProductId(updateProductRequest.id))
            ?: throw IllegalStateException("Product not found by id ${updateProductRequest.id}")

        val category = categoryDB.getById(CategoryId(updateProductRequest.categoryId ?: -1))

        val updated = foundProduct.copy(
            name = updateProductRequest.name ?: foundProduct.name,
            description = updateProductRequest.description ?: foundProduct.description,
            price = updateProductRequest.price ?: foundProduct.price,
            stockQuantity = updateProductRequest.stockQuantity ?: foundProduct.stockQuantity,
            categoryId = category?.categoryId ?: foundProduct.categoryId,
        )
        productDB.replace(updated)
        return updated
    }

    fun remove(productId: ProductId) {
        productDB.remove(productId)
    }
}