package com.example.deliveryapp.client.presentation.cartShopping

import com.example.deliveryapp.client.domain.model.Product

sealed interface ClientCartAction {
    data class OnRemoveQuantityCLick(val product: Product): ClientCartAction
    data class OnAddQuantityCLick(val product: Product): ClientCartAction
    data object OnContinueShopClick: ClientCartAction
    data class OnRemoveProductInCartClick(val product: Product): ClientCartAction
    data object OnUpdateAllCart: ClientCartAction
    data object OnBackClick: ClientCartAction
}