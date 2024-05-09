package io.bensmith.config

import io.ktor.server.config.*

data class DataSourceConfig(
    val host: String,
    val port: Int,
    val database: String,
    val schema: String?,
    val username: String,
    val password: String
) {

    fun getJdbcUrl(): String {

        if (schema !== null) {
            return "jdbc:postgresql://$host:$port/$database?currentSchema=$schema"
        }

        return "jdbc:postgresql://$host:$port/$database"
    }

    companion object {
        operator fun invoke(applicationConfig: ApplicationConfig): DataSourceConfig {
            return DataSourceConfig(
                host = applicationConfig.property("datasource.host").getString(),
                port = applicationConfig.property("datasource.port").getString().toInt(),
                database = applicationConfig.property("datasource.database").getString(),
                schema = applicationConfig.propertyOrNull("datasource.schema")?.getString(),
                username = applicationConfig.property("datasource.username").getString(),
                password = applicationConfig.property("datasource.password").getString(),
            )
        }
    }
}