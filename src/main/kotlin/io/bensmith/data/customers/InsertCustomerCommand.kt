package io.bensmith.data.customers

import io.bensmith.data.mappers.mapCustomer
import io.bensmith.models.Customer
import io.bensmith.models.CustomerToCreate
import java.sql.Statement
import javax.sql.DataSource

class InsertCustomerCommand(private val dataSource: DataSource) {

    private val query = """
        INSERT INTO customers (first_name, last_name, email) 
        VALUES (?, ?, ?)
    """.trimIndent()

    fun execute(customerToCreate: CustomerToCreate): Customer {

        lateinit var customer: Customer

        dataSource.connection.use { connection ->

            val statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)

            statement.use {

                statement.setString(1, customerToCreate.firstName)
                statement.setString(2, customerToCreate.lastName)
                statement.setString(3, customerToCreate.email)

                statement.executeUpdate()

                statement.generatedKeys.use { resultSet ->
                    while (resultSet.next()) {
                        return mapCustomer(resultSet)
                    }
                }
            }
        }

        return customer
    }
}