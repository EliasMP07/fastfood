package com.example.deliveryapp.core.domain.model.order

data class ProductClient(
    val description: String,
    val id: Int,
    val image1: String,
    val image2: String,
    val image3: String,
    val name: String,
    val price: Int,
    val quantity: Int
)