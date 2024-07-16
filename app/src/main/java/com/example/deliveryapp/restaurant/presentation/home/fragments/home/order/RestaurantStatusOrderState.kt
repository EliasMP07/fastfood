package com.example.deliveryapp.restaurant.presentation.home.fragments.home.order

import com.example.deliveryapp.core.domain.model.order.Order

data class RestaurantStatusOrderState(
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isNewOlderList: Boolean = false
)
