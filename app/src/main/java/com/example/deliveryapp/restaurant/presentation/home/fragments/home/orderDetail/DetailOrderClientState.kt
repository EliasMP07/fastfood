package com.example.deliveryapp.restaurant.presentation.home.fragments.home.orderDetail

import com.example.deliveryapp.restaurant.domain.model.DeliveryAvailable

data class DetailOrderClientState(
    val deliveriesAvailable: List<DeliveryAvailable> = emptyList(),
    val idDelivery: String = "",
)
