package com.example.deliveryapp.restaurant.presentation.home.fragments.product

sealed interface RestaurantProductAction {
    data object OnUploadImageClick: RestaurantProductAction
    data class OnImageChange(val image: List<String>): RestaurantProductAction
    data object OnCreateProductClick: RestaurantProductAction
    data class OnCategoryChange(val idCategory: String): RestaurantProductAction
    data class OnPriceProductChange(val price: Double): RestaurantProductAction
    data class OnDescriptionProductChange(val description: String): RestaurantProductAction
    data class OnNameProductChange(val name: String): RestaurantProductAction
}