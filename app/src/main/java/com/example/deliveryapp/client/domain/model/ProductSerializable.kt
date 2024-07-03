package com.example.deliveryapp.client.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductSerializable(
    val id: String = "",
    val name: String = "",
    val description: String= "",
    val price: Double = 0.0,
    val image: String= "",
    val image2: String= "",
    val image3: String= "",
    val idCategory: String= "",
    val ranting: Double= 0.0,
    val coupons: List<CouponSerializable> = emptyList()
)
