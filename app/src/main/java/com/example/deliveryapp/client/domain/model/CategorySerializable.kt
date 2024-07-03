package com.example.deliveryapp.client.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CategorySerializable(
    val id: String = "",
    val name: String = "",
    val image: String = ""
)
