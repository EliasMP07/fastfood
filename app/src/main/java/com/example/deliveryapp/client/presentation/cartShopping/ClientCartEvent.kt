package com.example.deliveryapp.client.presentation.cartShopping

import com.example.deliveryapp.core.presentation.ui.UiText

sealed interface ClientCartEvent {
    data class Success(val message: UiText): ClientCartEvent
    data class Error(val error: UiText): ClientCartEvent
}