package com.example.deliveryapp.client.presentation.address.list

sealed interface ClientMyAddressAction {
    data object OnBackClick: ClientMyAddressAction
    data class OnSelectedAddress(val addressId: String): ClientMyAddressAction
}