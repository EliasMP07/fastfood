package com.example.deliveryapp.restaurant.presentation.home.fragments.category

import com.example.deliveryapp.core.presentation.ui.UiText

sealed interface RestaurantCategoryEvent {
    data object OnSuccess: RestaurantCategoryEvent
    data class Error(val error: UiText): RestaurantCategoryEvent
}