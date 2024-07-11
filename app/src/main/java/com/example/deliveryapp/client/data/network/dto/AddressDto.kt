package com.example.deliveryapp.client.data.network.dto

import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("id") val id: String,
    @SerializedName("id_user") val idUser: String?,
    @SerializedName("address") val address: String,
    @SerializedName("neighborhood") val neighborhood: String?,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
)
