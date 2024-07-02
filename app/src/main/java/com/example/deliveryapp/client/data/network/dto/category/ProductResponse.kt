package com.example.deliveryapp.client.data.network.dto.category

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("error") val error: String?,
    @SerializedName("data") val products: List<ProductDto>

)
