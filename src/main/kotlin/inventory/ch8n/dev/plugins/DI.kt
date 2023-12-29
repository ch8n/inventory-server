package inventory.ch8n.dev.plugins

import inventory.ch8n.dev.data.database.CategoryDB
import inventory.ch8n.dev.data.database.ProductDB
import inventory.ch8n.dev.usecases.GetProduct
import inventory.ch8n.dev.usecases.UpsertProduct
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
    single { CategoryDB() }
    single { GetProduct(get()) }
    single { UpsertProduct(get(), get()) }
}
