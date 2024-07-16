package com.example.deliveryapp.restaurant.domain.model

data class OrderRestaurant(
    val address: AddressClient,
    val client:Client,
    val delivery: Delivery,
    val id: String,
    val idAddress: String,
    val idClient: String,
    val idDelivery: String,
    val lat: String,
    val lng: String,
    val productsClient: List<ProductClient>,
    val status: String,
    val timestamp: String
)