package com.example.deliveryapp.restaurant.presentation.home.fragments.product

import com.example.deliveryapp.core.presentation.ui.UiText

sealed interface RestaurantProductEvent {
    data class Success(val message: String): RestaurantProductEvent
    data class Error(val error: UiText): RestaurantProductEvent
}