package com.example.deliveryapp.core.user.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val lastname: String = "",
    val email: String = "",
    val image: String = "",
    val phone: String = "",
    val sessionToken: String = "",
    val roles: List<Rol>? = null
)
