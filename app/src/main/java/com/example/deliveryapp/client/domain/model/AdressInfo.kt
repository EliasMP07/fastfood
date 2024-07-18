package com.example.deliveryapp.client.domain.model


data class AddressInfo(
    val address: String = "",
    val country: String = "",
    val city: String = "",
    val lat: Double = 0.0,
    val log: Double = 0.0
)