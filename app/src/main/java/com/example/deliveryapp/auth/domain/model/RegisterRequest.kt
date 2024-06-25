package com.example.deliveryapp.auth.domain.model

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val lastname: String,
    val phone: String,
    val image: String?
)
