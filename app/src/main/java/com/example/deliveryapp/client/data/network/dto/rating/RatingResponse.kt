package com.example.deliveryapp.client.data.network.dto.rating

import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("error") val error: String?
)
