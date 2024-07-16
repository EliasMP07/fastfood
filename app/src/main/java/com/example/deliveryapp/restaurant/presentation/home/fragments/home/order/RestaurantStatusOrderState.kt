package com.example.deliveryapp.restaurant.presentation.home.fragments.home.order

import com.example.deliveryapp.restaurant.domain.model.OrderRestaurant

data class RestaurantStatusOrderState(
    val orders: List<OrderRestaurant> = emptyList()
)
