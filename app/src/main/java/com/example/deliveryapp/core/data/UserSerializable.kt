package com.example.deliveryapp.core.data

import kotlinx.serialization.Serializable

@Serializable
data class UserSerializable(
    val id: String,
    val name: String,
    val lastname: String,
    val email: String,
    val image: String,
    val phone: String,
    val sessionToken: String,
    val roles: List<RolSerializable>?
)