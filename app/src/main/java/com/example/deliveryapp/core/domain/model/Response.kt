package com.example.deliveryapp.core.domain.model

import com.example.deliveryapp.core.presentation.ui.UiText


sealed class Response<out T> {
    data object Loading : Response<Nothing>()
    data class Success<T>(val data: T) : Response<T>()
    data class Failure(val error: UiText) : Response<Nothing>()
}