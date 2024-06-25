package com.example.deliveryapp.auth.presentation.login

/*
Interfaz que maneja los eventos de inicio de Sesiòn
 */
sealed interface LoginEvent {
    data object Success: LoginEvent
    data class Error(val error: String): LoginEvent
}