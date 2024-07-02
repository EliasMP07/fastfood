package com.example.deliveryapp.restaurant.presentation.home.fragments.product

sealed interface RestaurantProductEvent {
    data class Success(val message: String): RestaurantProductEvent
    data class Error(val error: String): RestaurantProductEvent
}