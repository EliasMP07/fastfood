package com.example.deliveryapp.client.presentation.home.fragments.home

sealed interface HomeAction {
    data object OnCartClick: HomeAction
    data object OnRetryAgainClick: HomeAction
}