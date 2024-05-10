package io.bensmith.models

import kotlinx.serialization.Serializable

@Serializable
data class CustomerToCreate(
    val firstName: String,
    val lastName: String,
    val email: String
)