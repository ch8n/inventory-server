package inventory.ch8n.dev.data.database

import inventory.ch8n.dev.data.models.*
import kotlin.random.Random

class OffersDB {

    private val offers = mutableListOf<Offer>()

    init {
        repeat(3) {
            offers.add(
                Offer(
                    offerId = OfferId(value = it.toLong()),
                    bannerUrl = "https://picsum.photos/200/300",
                )
            )
        }
    }

    fun add(offer: Offer) {
        offers.add(offer)
    }

    fun replace(offer: Offer) {
        offers.replaceAll {
            if (it.offerId.value == offer.offerId.value) {
                offer
            } else {
                it
            }
        }
    }

    fun remove(offerId: OfferId) {
        offers.removeIf { it.offerId.value == offerId.value }
    }

    fun getAll() = offers

    fun getById(offerId: OfferId) = offers.find { it.offerId.value == offerId.value }
}