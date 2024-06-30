package com.example.deliveryapp.restaurant.presentation.home.fragments.profile

sealed interface RestaurantProfileAction {

    data object OnEditProfileClick: RestaurantProfileAction
    data object OnSelectRolClick: RestaurantProfileAction
    data object OnLogoutClick: RestaurantProfileAction

}