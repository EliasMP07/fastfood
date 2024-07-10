package com.example.deliveryapp.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DeliveryApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: String?,
    @SerializedName("data") val data: T?
)

