package com.example.deliveryapp.delivery.presentation.home.fragments.profileDelivery

import com.example.deliveryapp.client.presentation.home.fragments.profile.ProfileClientAction

sealed interface ProfileDeliveryAction {

    data object OnEditProfileClick: ProfileDeliveryAction
    data object OnLogoutClick: ProfileDeliveryAction
    data object OnSelectedRolClick: ProfileDeliveryAction

}