package inventory.ch8n.dev.data.models

import kotlinx.serialization.Serializable


@JvmInline
@Serializable
value class OfferId(val value: Long)

@Serializable
data class Offer(
    val offerId: OfferId,
    val bannerUrl: String
)

