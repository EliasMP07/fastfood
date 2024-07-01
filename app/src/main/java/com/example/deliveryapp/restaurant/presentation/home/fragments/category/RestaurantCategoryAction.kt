package com.example.deliveryapp.restaurant.presentation.home.fragments.category

sealed interface RestaurantCategoryAction {
    data object OnCreateCategoryClick: RestaurantCategoryAction
    data class OnImageGalleryChange(val image: String): RestaurantCategoryAction
    data class OnImageCamaraChange(val image: String): RestaurantCategoryAction
    data object OnTakeImageSelected: RestaurantCategoryAction
    data class OnNameCategoryChange(val name: String): RestaurantCategoryAction
}