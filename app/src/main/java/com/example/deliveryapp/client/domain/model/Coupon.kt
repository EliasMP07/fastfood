package com.example.deliveryapp.client.domain.model

data class Coupon(
    val id: Long,
    val code: String,
    val discountPercentage: Double,
    val expirationDate: String,
)