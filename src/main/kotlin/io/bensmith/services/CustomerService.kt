package io.bensmith.services

import io.bensmith.data.customers.DeleteCustomerCommand
import io.bensmith.data.customers.GetCustomerByIdQuery
import io.bensmith.data.customers.GetCustomersQuery
import io.bensmith.data.customers.InsertCustomerCommand
import io.bensmith.models.Customer
import io.bensmith.models.CustomerToCreate

class CustomerService(
    private val getCustomersQuery: GetCustomersQuery,
    private val getCustomerByIdQuery: GetCustomerByIdQuery,
    private val insertCustomerCommand: InsertCustomerCommand,
    private val deleteCustomerCommand: DeleteCustomerCommand
) {

    fun getAll(): List<Customer> {
        return getCustomersQuery.execute()
    }

    fun getById(id: Int): Customer? {
        return getCustomerByIdQuery.execute(id)
    }

    fun create(customer: CustomerToCreate): Customer {
        return insertCustomerCommand.execute(customer)
    }

    fun delete(id: Int): Boolean {
        return deleteCustomerCommand.execute(id)
    }
}