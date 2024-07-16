package com.example.deliveryapp.core.domain.model.order

data class Order(
    val address: AddressClient,
    val client: Client,
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