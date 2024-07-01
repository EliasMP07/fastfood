package com.example.deliveryapp.client.data.network.dto.category

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("id")val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String
)
