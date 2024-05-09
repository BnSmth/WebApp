package io.bensmith.data.mappers

import io.bensmith.models.Customer
import java.sql.ResultSet

fun mapCustomer(resultSet: ResultSet): Customer {

    val id = resultSet.getInt(1)
    val firstName = resultSet.getString(2)
    val lastName = resultSet.getString(3)
    val email = resultSet.getString(4)

    return Customer(id, firstName, lastName, email)
}