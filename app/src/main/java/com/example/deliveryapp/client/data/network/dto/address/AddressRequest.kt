package com.example.deliveryapp.client.data.network.dto.address

import com.google.gson.annotations.SerializedName

data class AddressRequest(
    @SerializedName("id_user")val idUser: String,
    @SerializedName("address")val address: String,
    @SerializedName("neighborhood")val neighborhood: String,
    @SerializedName("lat")val latitud: Double,
    @SerializedName("lng")val longitud: Double
)
