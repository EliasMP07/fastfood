package com.example.deliveryapp.restaurant.domain.model

data class AddressClient(
    val address: String,
    val id: Int,
    val lat: Double,
    val lng: Double,
    val neighborhood: String
)