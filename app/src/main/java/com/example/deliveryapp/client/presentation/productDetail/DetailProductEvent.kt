package com.example.deliveryapp.client.presentation.productDetail

import com.example.deliveryapp.core.presentation.ui.UiText

sealed interface DetailProductEvent {
    data class Success(val message: UiText): DetailProductEvent
    data class Error(val error:UiText): DetailProductEvent
}