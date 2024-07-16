package com.example.deliveryapp.restaurant.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ClientDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("name")
    val name: String
)