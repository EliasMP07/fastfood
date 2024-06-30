package com.example.deliveryapp.client.presentation.home.fragments.profile

sealed interface ProfileClientAction {
    data object OnEditProfileClick: ProfileClientAction
    data object OnLogoutClick: ProfileClientAction
}