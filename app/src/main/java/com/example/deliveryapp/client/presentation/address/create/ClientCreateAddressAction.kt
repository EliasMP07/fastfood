package com.example.deliveryapp.client.presentation.address.create

import com.example.deliveryapp.client.presentation.address.models.AddressInfo

sealed interface ClientCreateAddressAction {
    data object OnMapSelectPointReferenceClick: ClientCreateAddressAction
    data class OnReferenceMapChange(val addressInfo: AddressInfo): ClientCreateAddressAction
    data class OnAddressChange(val address: String): ClientCreateAddressAction
    data class OnNeighborhoodChange(val neighborhood: String): ClientCreateAddressAction
    data object OnCreateAddressClick: ClientCreateAddressAction
    data object OnBackClick: ClientCreateAddressAction
}