package com.example.deliveryapp.delivery.presentation.home.fragments.orders

import com.example.deliveryapp.core.domain.model.order.Order

data class DeliveryPageOrderState(
    val orders: List<Order> = emptyList()
)
