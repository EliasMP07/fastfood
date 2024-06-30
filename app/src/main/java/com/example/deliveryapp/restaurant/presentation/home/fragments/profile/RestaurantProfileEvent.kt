package com.example.deliveryapp.restaurant.presentation.home.fragments.profile

import com.example.deliveryapp.core.user.domain.model.User

sealed interface RestaurantProfileEvent {
    data class Success(val user: User): RestaurantProfileEvent
    data class Error(val error: String): RestaurantProfileEvent
}