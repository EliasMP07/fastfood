package com.example.deliveryapp.client.data.network.dto.rating

import com.google.gson.annotations.SerializedName

data class RatingRequest(
    @SerializedName("product_id") val productId: String,
    @SerializedName("user_id")val userId: String,
    @SerializedName("rating") val rating: Double
)
