package com.example.deliveryapp.core.data.remote.dto

import com.example.deliveryapp.core.user.data.network.dto.UserDto
import com.google.gson.annotations.SerializedName

data class DeliveryApiResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: String?,
    @SerializedName("data") val userDto: UserDto?
)
