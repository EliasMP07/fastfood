package com.example.deliveryapp.auth.domain.model

sealed class Response<out T> {
    data class Success<out T>(val data: T): Response<T>()
    data class Failure<out T>(val exception: Exception?): Response<T>()
}
