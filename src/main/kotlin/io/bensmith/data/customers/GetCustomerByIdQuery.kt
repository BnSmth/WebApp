package io.bensmith.data.customers

import io.bensmith.data.mappers.mapCustomer
import io.bensmith.models.Customer
import javax.sql.DataSource

class GetCustomerByIdQuery(private val dataSource: DataSource) {

    private val query = """
        SELECT id, first_name, last_name, email
        FROM customers
        WHERE id = ?
    """.trimIndent()

    fun execute(id: Int): Customer? {

        dataSource.connection.use { connection ->
            connection.prepareStatement(query).use { statement ->
                statement.setInt(1, id)
                statement.executeQuery().use { resultSet ->
                    while (resultSet.next()) {
                        return mapCustomer(resultSet)
                    }
                }
            }
        }

        return null
    }
}