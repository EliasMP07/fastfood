package com.example.deliveryapp.restaurant.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ProductClientDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image1")
    val image1: String,
    @SerializedName("image2")
    val image2: String,
    @SerializedName("image3")
    val image3: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: Int
)