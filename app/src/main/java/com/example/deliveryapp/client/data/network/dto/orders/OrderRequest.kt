package com.example.deliveryapp.client.data.network.dto.orders

import com.example.deliveryapp.client.data.network.dto.category.ProductDto
import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("id_client") val idClient: String,
    @SerializedName("id_address") val idAddress: String,
    @SerializedName("status") val status: String,
    @SerializedName("products") val products: List<ProductDto>
)
