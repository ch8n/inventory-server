package inventory.ch8n.dev.controllers

import inventory.ch8n.dev.data.models.*
import inventory.ch8n.dev.usecases.GetOffersUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Routing.offerController() {
    route("v1/offers") {
        getOfferBanners()
    }
}

fun Route.getOfferBanners() {
    val getOffersUseCase by inject<GetOffersUseCase>()
    get {
        try {
            val offers = getOffersUseCase.getOfferBanners()
            call.respond(
                HttpStatusCode.OK,
                Response<List<Offer>>(data = offers)
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                Response<List<Offer>>(
                    error = ResponseError(
                        serverError = e.message ?: e.localizedMessage ?: "",
                        clientError = "Something went wrong!"
                    )
                )
            )
        }
    }
}


