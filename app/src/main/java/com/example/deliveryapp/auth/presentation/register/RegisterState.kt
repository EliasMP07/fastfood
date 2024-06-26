package com.example.deliveryapp.auth.presentation.register

import com.example.deliveryapp.auth.domain.validation.PasswordValidationState

data class RegisterState(
    val name: String = "",
    val isValidEmail: Boolean = true,
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val canRegister: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isLoading: Boolean = false
)