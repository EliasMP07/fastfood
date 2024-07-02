package com.example.deliveryapp.restaurant.presentation.home.fragments.product

import com.example.deliveryapp.client.domain.model.Category

data class RestaurantProductState(
    val image: List<String> = listOf(),
    val categories: List<Category> = listOf(),
    val name: String = "",
    val description: String = "",
    val idCategory: String = "",
    val price: Double = 0.0,
    val isLoading: Boolean= false
)
