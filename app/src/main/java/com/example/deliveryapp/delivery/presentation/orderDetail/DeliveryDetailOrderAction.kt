package com.example.deliveryapp.delivery.presentation.orderDetail

import com.example.deliveryapp.core.domain.model.order.Order

sealed interface DeliveryDetailOrderAction {
    data class InitDeliveryClick(val order: Order): DeliveryDetailOrderAction
}