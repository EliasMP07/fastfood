package com.example.deliveryapp.client.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CouponSerializable(
    val id: Long,
    val code: String,
    val discountPercentage: Double,
    val expirationDate: String,
)
