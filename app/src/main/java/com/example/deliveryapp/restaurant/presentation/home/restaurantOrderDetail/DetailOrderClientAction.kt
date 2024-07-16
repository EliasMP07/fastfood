package com.example.deliveryapp.restaurant.presentation.home.restaurantOrderDetail

import com.example.deliveryapp.core.domain.model.order.Order

sealed interface DetailOrderClientActions {
    data class AssignDeliveryAction(val order: Order): DetailOrderClientActions
    data class OnDeliveryChange(val deliveryId: String): DetailOrderClientActions
}