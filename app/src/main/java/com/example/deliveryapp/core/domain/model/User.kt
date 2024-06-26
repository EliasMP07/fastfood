package com.example.deliveryapp.core.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val name: String,
    val lastname: String,
    val email: String,
    val image: String,
    val phone: String,
    val sessionToken: String
)
