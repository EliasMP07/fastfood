package com.example.deliveryapp.restaurant.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: String?,
    @SerializedName("data") val id: String
)
