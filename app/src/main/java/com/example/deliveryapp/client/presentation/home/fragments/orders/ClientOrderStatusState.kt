package com.example.deliveryapp.client.presentation.home.fragments.orders

import com.example.deliveryapp.client.domain.model.OrderClient

data class ClientOrderStatusState(
    val listOrders: List<OrderClient> = emptyList(),
    val isLoading: Boolean = false
)
