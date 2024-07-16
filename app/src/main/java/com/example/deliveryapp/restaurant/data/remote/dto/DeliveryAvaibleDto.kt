package com.example.deliveryapp.restaurant.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DeliveryAvailableDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("session_token")
    val sessionToken: String
)
