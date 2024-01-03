package inventory.ch8n.dev.usecases

import inventory.ch8n.dev.data.database.CategoryDB
import inventory.ch8n.dev.data.database.ProductDB
import inventory.ch8n.dev.data.models.*
import io.ktor.http.content.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files
import kotlin.random.Random

class GetProductUsecases(private val productDB: ProductDB) {
    fun all() = productDB.getAll()
    fun getId(productId: ProductId) = productDB.getById(productId)
}

class UpdateProductUsecases(
    private val productDB: ProductDB,
    private val categoryDB: CategoryDB,
) {
    suspend fun create(createProductRequest: CreateProductRequest): Product {
        val category = categoryDB
            .getById(CategoryId(createProductRequest.categoryId))
            ?: throw IllegalArgumentException("Category not found!")
        val product = Product(
            productId = ProductId(value = Random.nextLong()),
            name = createProductRequest.name,
            description = createProductRequest.description,
            price = createProductRequest.price,
            stockQuantity = createProductRequest.stockQuantity,
            imageUrls = emptyList(),
            categoryId = category.categoryId,
            variants = listOf()
        )
        productDB.add(product)
        return product
    }

    suspend fun update(updateProductRequest: UpdateProductRequest): Product {
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

    suspend fun remove(productId: ProductId) {
        productDB.remove(productId)
    }

    suspend fun addVariant(addProductVariantRequest: AddProductVariantRequest): Product {
        val foundProduct = productDB.getById(ProductId(addProductVariantRequest.productId))
            ?: throw IllegalStateException("Product not found by id ${addProductVariantRequest.productId}")

        val variantProduct = productDB.getById(ProductId(addProductVariantRequest.variantProductId))
            ?: throw IllegalStateException("Product variant not found by id ${addProductVariantRequest.variantProductId}")

        val updated = foundProduct.copy(variants = foundProduct.variants + variantProduct.productId)
        productDB.replace(updated)
        return updated
    }

    suspend fun removeVariant(removeProductVariantRequest: RemoveProductVariantRequest): Product {
        val foundProduct = productDB.getById(ProductId(removeProductVariantRequest.productId))
            ?: throw IllegalStateException("Product not found by id ${removeProductVariantRequest.productId}")

        val deleteProductId = ProductId(removeProductVariantRequest.variantProductId)

        val updated = foundProduct.copy(variants = foundProduct.variants.filter { id ->
            id.value != deleteProductId.value
        })
        productDB.replace(updated)
        return updated
    }

    suspend fun uploadFile(multiPartData: MultiPartData, productId: ProductId): Product {
        val found =
            productDB.getById(productId) ?: throw IllegalStateException("Product not found by id ${productId.value}")
        val part = multiPartData.readPart() ?: throw IllegalStateException("No content received!")
        if (part is PartData.FileItem) {
            val fileName = part.originalFileName ?: throw IllegalStateException("File name not found!")
            val ext = File(fileName).extension
            val uploadFileName = found.name.replace(" ", "_")
                .plus(found.imageUrls.size + 1)
                .plus(".")
                .plus(ext)

            val file = File("uploads/$uploadFileName")

            withContext(Dispatchers.IO) {
                Files.createDirectories(file.parentFile.toPath())
                val byteArray = part.provider().readBytes()
                file.writeBytes(byteArray)
            }

            val updated = found.copy(imageUrls = found.imageUrls + file.path)
            productDB.replace(updated)
            part.dispose()
            return updated
        } else {
            part.dispose()
            throw IllegalStateException("Wrong file format!")
        }
    }

    fun deleteImageOfProduct(deleteProductImage: RemoveProductImageRequest): Product {
        val productId = ProductId(deleteProductImage.productId)
        val imageUrl = deleteProductImage.imageUrl
        val found = productDB.getById(productId)
            ?: throw IllegalStateException("Product not found by id ${productId.value}")
        val updated = found.copy(imageUrls = found.imageUrls.filter { it != imageUrl })
        productDB.replace(updated)
        return updated
    }
}