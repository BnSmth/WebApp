package io.bensmith.modules

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.bensmith.config.DataSourceConfig
import io.ktor.server.config.*
import io.bensmith.data.customers.DeleteCustomerCommand
import io.bensmith.data.customers.GetCustomerByIdQuery
import io.bensmith.data.customers.GetCustomersQuery
import io.bensmith.data.customers.InsertCustomerCommand
import io.bensmith.services.CustomerService
import org.koin.dsl.module
import javax.sql.DataSource

fun applicationModule(applicationConfig: ApplicationConfig) = module {

    // Config
    single { DataSourceConfig(applicationConfig) }

    // Data
    single<DataSource> {
        val config = get<DataSourceConfig>()

        val hikariConfig = HikariConfig().apply {
            jdbcUrl = config.getJdbcUrl()
            username = config.username
            password = config.password
            maximumPoolSize = 3
            minimumIdle = 1
            idleTimeout = 10000
        }

        HikariDataSource(hikariConfig)
    }

    single { GetCustomersQuery(get()) }
    single { GetCustomerByIdQuery(get()) }
    single { InsertCustomerCommand(get()) }
    single { DeleteCustomerCommand(get()) }

    // Services
    single { CustomerService(get(), get(), get(), get()) }
}