package com.example.deliveryapp.client.data.network.dto.orders


import com.example.deliveryapp.client.data.network.dto.AddressDto
import com.example.deliveryapp.client.data.network.dto.category.ProductDto
import com.google.gson.annotations.SerializedName

data class OrderClientDto(
    @SerializedName("id") val id: String,
    @SerializedName("status") val status: String,
    @SerializedName("id_delivery") val idDelivery: String?,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("address") val address: AddressDto,
    @SerializedName("products") val products: List<ProductDto>
)
