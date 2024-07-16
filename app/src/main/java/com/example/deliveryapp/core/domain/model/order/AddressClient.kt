package com.example.deliveryapp.core.domain.model.order

data class AddressClient(
    val address: String,
    val id: Int,
    val lat: Double,
    val lng: Double,
    val neighborhood: String
)