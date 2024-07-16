package com.example.deliveryapp.restaurant.presentation.home.restaurantOrderDetail

import com.example.deliveryapp.core.presentation.ui.UiText

sealed interface DetailOrderClientEvent {
    data class Success(val message: UiText): DetailOrderClientEvent
    data class Error(val message: UiText): DetailOrderClientEvent
}