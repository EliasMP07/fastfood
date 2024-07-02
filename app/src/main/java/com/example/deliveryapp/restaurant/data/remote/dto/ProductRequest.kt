package com.example.deliveryapp.restaurant.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("image1") val image1: String,
    @SerializedName("id_category") val idCategory: String,
    @SerializedName("image2") val image2: String,
    @SerializedName("image3") val image3: String
)
