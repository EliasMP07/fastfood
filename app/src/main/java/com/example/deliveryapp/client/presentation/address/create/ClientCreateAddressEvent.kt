package com.example.deliveryapp.client.presentation.address.create

import com.example.deliveryapp.core.presentation.ui.UiText

sealed interface ClientCreateAddressEvent {
    data class OnSuccess(val message: UiText): ClientCreateAddressEvent
    data class OnError(val error: UiText): ClientCreateAddressEvent
}