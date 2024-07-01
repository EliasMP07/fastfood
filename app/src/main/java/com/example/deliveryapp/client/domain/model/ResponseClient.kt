package com.example.deliveryapp.client.domain.model

sealed class ResponseClient <out T> {
    data object Loading: ResponseClient<Nothing>()
    data class Success<out T>(val data: T): ResponseClient<T>()
    data class Failure<out T>(val exception: Exception?): ResponseClient<T>()
}