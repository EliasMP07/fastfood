package com.example.deliveryapp.auth.data.remote

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
    @SerializedName("name")val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("image")val image: String?,
    @SerializedName("phone")val phone: String,
)
