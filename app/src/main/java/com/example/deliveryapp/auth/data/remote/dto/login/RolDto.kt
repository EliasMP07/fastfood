package com.example.deliveryapp.auth.data.remote.dto.login

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class RolDto(
    @SerializedName("id")val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("route") val route: String
)
