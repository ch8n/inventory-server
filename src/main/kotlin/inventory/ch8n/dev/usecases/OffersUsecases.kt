package inventory.ch8n.dev.usecases

import inventory.ch8n.dev.data.database.CategoryDB
import inventory.ch8n.dev.data.database.OffersDB
import inventory.ch8n.dev.data.models.*
import kotlin.random.Random

class GetOffersUseCase(private val offersDB: OffersDB) {
    fun getOfferBanners() = offersDB.getAll()
    fun getId(offerId: OfferId) = offersDB.getById(offerId)
}

class UpdateOfferUseCase(
    private val categoryDB: CategoryDB
) {
    fun create(createCategoryRequest: CreateCategoryRequest): Category {
        val found = categoryDB.getAll().find { it.name.equals(createCategoryRequest.name, ignoreCase = true) }
        if (found != null) throw IllegalArgumentException("Category already exist!")

        val category = Category(
            categoryId = CategoryId(value = Random.nextLong()),
            name = createCategoryRequest.name
        )
        categoryDB.add(category)
        return category
    }

    fun update(updateCategoryRequest: UpdateCategoryRequest): Category {
        val found = categoryDB.getById(CategoryId(updateCategoryRequest.categoryId))
            ?: throw IllegalArgumentException("Category not found!")
        val updated = found.copy(name = updateCategoryRequest.name)
        categoryDB.replace(updated)
        return updated
    }

    fun remove(deleteCategoryRequest: DeleteCategoryRequest) {
        categoryDB.remove(CategoryId(deleteCategoryRequest.categoryId))
    }
}