package com.example.deliveryapp.core.data.remote.dto.orders

import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("address")
    val address: AddressClientDto,
    @SerializedName("client")
    val clientDto: ClientDto,
    @SerializedName("delivery")
    val deliveryDto: DeliveryDto,
    @SerializedName("id")
    val id: String,
    @SerializedName("id_address")
    val idAddress: String,
    @SerializedName("id_client")
    val idClient: String,
    @SerializedName("id_delivery")
    val idDelivery: String?,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("products")
    val productClientDto: List<ProductClientDto>,
    @SerializedName("status")
    val status: String,
    @SerializedName("timestamp")
    val timestamp: String
)
