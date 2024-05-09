package io.bensmith.data.customers

import io.bensmith.data.mappers.mapCustomer
import io.bensmith.models.Customer
import javax.sql.DataSource

class GetCustomersQuery(private val dataSource: DataSource) {

    private val query = """
        SELECT id, first_name, last_name, email
        FROM customers
    """.trimIndent()

    fun execute(): List<Customer> {

        val customers = mutableListOf<Customer>()

        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery(query).use { resultSet ->
                    while (resultSet.next()) {
                        val customer = mapCustomer(resultSet)
                        customers.add(customer)
                    }
                }
            }
        }

        return customers
    }
}