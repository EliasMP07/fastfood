package com.example.deliveryapp.auth.data.remote.dto

import com.example.deliveryapp.auth.data.remote.dto.login.UserDto
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: String?,
    @SerializedName("data") val userDto: UserDto?
)
