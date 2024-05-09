package io.bensmith.data.customers

import javax.sql.DataSource

class DeleteCustomerCommand(private val dataSource: DataSource) {

    private val query = """
        DELETE FROM customers
        WHERE id = ?
    """.trimIndent()

    fun execute(id: Int): Boolean {

        dataSource.connection.use { connection ->
            connection.prepareStatement(query).use { statement ->
                statement.setInt(1, id)

                val result = statement.executeUpdate()

                if (result == 1) {
                    return true
                }
            }
        }

        return false
    }
}