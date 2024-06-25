package com.example.deliveryapp.auth.presentation.login

/**
 * Interfaz que maneja las acciones
 */
sealed interface LoginAction {
    data object OnLoginClick: LoginAction
    data object OnRegisterClick: LoginAction
    data class OnEmailChange(val email: String): LoginAction
    data class OnPasswordChange(val password: String): LoginAction
}