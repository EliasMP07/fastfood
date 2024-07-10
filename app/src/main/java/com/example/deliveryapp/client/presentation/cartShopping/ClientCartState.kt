package com.example.deliveryapp.client.presentation.cartShopping

import com.example.deliveryapp.client.domain.model.Product

data class ClientCartState(
    val productCart: List<Product> = emptyList(),
    val totalPrice: Double = 0.0
)
