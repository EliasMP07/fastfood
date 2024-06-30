package com.example.deliveryapp.core.user.data.network.dto

import com.google.gson.annotations.SerializedName

data class UserRequestDto(
    @SerializedName("id") val id: String,
    @SerializedName("name")val name: String,
    @SerializedName("lastname")val lastname: String,
    @SerializedName("email")val email: String,
    @SerializedName("phone")val phone: String,
    @SerializedName("image")val image: String?
)
