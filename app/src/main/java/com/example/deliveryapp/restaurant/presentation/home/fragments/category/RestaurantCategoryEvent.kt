package com.example.deliveryapp.restaurant.presentation.home.fragments.category

sealed interface RestaurantCategoryEvent {
    data object OnSuccess: RestaurantCategoryEvent
    data class Error(val error: String): RestaurantCategoryEvent
}