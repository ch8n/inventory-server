package inventory.ch8n.dev.usecases

import inventory.ch8n.dev.data.database.CategoryDB
import inventory.ch8n.dev.data.database.OffersDB
import inventory.ch8n.dev.data.models.*
import kotlin.random.Random

class GetOffersUseCase(private val offersDB: OffersDB) {
    fun getOfferBanners() = offersDB.getAll()
    fun getId(offerId: OfferId) = offersDB.getById(offerId)
}
