package com.example.deliveryapp.restaurant.presentation.home.fragments.home.orderDetail

import com.example.deliveryapp.restaurant.domain.model.OrderRestaurant

sealed interface DetailOrderClientActions {
    data class AssignDeliveryAction(val order: OrderRestaurant): DetailOrderClientActions
    data class OnDeliveryChange(val deliveryId: String): DetailOrderClientActions
}