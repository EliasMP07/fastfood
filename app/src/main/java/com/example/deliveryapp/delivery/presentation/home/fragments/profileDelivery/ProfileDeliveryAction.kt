package com.example.deliveryapp.delivery.presentation.home.fragments.profileDelivery

sealed interface ProfileDeliveryAction {

    data object OnEditProfileClick: ProfileDeliveryAction
    data object OnLogoutClick: ProfileDeliveryAction
    data object OnSelectedRolClick: ProfileDeliveryAction

}