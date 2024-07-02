package com.example.deliveryapp.client.domain.model

import com.google.gson.annotations.SerializedName

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val image: String,
    val image2: String,
    val image3: String,
    val idCategory: String,
    val ranting: Double,
    val coupons: List<Coupon> = emptyList()
) {
    val discountedPrice: Double
        get() {
            val maxDiscount = coupons.maxByOrNull { it.discountPercentage }?.discountPercentage ?: 0.0
            return price * (1 - maxDiscount / 100)
        }
}