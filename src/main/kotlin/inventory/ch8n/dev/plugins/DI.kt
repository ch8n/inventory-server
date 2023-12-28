package inventory.ch8n.dev.plugins

import inventory.ch8n.dev.controllers.GetProducts
import inventory.ch8n.dev.data.database.ProductDB
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.koinDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}


val appModule = module {
    single { ProductDB() }
    single { GetProducts(get()) }
}
