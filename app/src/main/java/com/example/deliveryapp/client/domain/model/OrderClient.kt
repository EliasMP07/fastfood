package com.example.deliveryapp.client.domain.model

data class OrderClient(
    val id: String,
    val status: String,
    val timestamp: String,
    val address: Address,
    val products: List<Product>
)
