package com.example.deliveryapp.restaurant.presentation.home.fragments.category

data class RestaurantCategoryState(
    val categoryName: String = "",
    val isLoading: Boolean = false,
    val isValidSave: Boolean = false,
    val image: String = "",
    val imagePreview: String = ""
)
