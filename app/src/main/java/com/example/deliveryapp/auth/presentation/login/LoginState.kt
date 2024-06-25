package com.example.deliveryapp.auth.presentation.login

/*
*Data class que maneja los estados de inicio de sesión
 */
data class LoginState(
    val email: String = "",
    val password: String = "",
    val canLogged: Boolean = false,
    val isLoading: Boolean = false
)
