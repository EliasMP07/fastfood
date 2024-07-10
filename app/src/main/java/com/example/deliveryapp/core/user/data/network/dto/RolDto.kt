package com.example.deliveryapp.core.user.data.network.dto

import com.google.gson.annotations.SerializedName

data class RolDto(
    @SerializedName("id")val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("route") val route: String
)
