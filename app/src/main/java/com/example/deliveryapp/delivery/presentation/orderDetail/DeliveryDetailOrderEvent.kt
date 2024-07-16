package com.example.deliveryapp.delivery.presentation.orderDetail

import com.example.deliveryapp.core.presentation.ui.UiText

sealed interface DeliveryDetailOrderEvent {
    data class Success(val message: UiText): DeliveryDetailOrderEvent
    data class Error(val error: UiText): DeliveryDetailOrderEvent
}