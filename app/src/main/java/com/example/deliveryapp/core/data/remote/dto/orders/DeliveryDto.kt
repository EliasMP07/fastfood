package com.example.deliveryapp.core.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class DeliveryDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("name")
    val name: String?
)