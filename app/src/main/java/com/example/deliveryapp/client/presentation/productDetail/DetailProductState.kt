package com.example.deliveryapp.client.presentation.productDetail

import com.example.deliveryapp.client.domain.model.Product

data class DetailProductState(
    val product: Product = Product(),
    val rating: Double = 0.0
)
