package com.example.deliveryapp.auth.domain.usecases

data class AuthUseCases(
    val login: LoginWithEmailAndPasswordUseCase,
    val register: RegisterUseCase
)
