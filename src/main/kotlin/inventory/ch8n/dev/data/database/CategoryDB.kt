package inventory.ch8n.dev.data.database

import inventory.ch8n.dev.data.models.Category
import inventory.ch8n.dev.data.models.CategoryId
import inventory.ch8n.dev.data.models.Product
import inventory.ch8n.dev.data.models.ProductId
import kotlin.random.Random

class CategoryDB {

    private val categories = mutableListOf<Category>()

    init {
        repeat(3) {
            categories.add(
                Category(
                    categoryId = CategoryId(value = it.toLong()),
                    name = Random.nextDouble().toString() + it
                )
            )
        }
    }

    fun add(category: Category) {
        categories.add(category)
    }

    fun replace(category: Category) {
        categories.replaceAll {
            if (it.categoryId.value == category.categoryId.value) {
                category
            } else {
                it
            }
        }
    }

    fun remove(categoryId: CategoryId) {
        categories.removeIf { it.categoryId.value == categoryId.value }
    }

    fun getAll() = categories

    fun getById(categoryId: CategoryId) = categories.find { it.categoryId.value == categoryId.value }
}