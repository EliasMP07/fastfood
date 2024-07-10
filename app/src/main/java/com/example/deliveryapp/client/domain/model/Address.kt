package com.example.deliveryapp.client.domain.model

data class Address(
    val id: String = "",
    val idUser: String,
    val address: String,
    val neighborhood: String,
    val lat: Double,
    val lng: Double,
)
