package com.example.deliveryapp.core.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class AddressClientDto(
    @SerializedName("address")
    val address: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("neighborhood")
    val neighborhood: String
)