package com.example.deliveryapp.auth.presentation.login

import com.example.deliveryapp.core.user.domain.model.User

/*
Interfaz que maneja los eventos de inicio de Sesiòn
 */
sealed interface LoginEvent {
    data class Success(val user: User): LoginEvent
    data class Error(val error: String): LoginEvent
}