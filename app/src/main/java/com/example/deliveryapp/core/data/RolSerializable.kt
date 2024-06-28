package com.example.deliveryapp.core.data

import kotlinx.serialization.Serializable

@Serializable
data class RolSerializable(
    val id: String,
    val name: String,
    val image: String,
    val route: String
)
