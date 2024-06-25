package com.example.deliveryapp.auth.domain.usecases

import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.auth.domain.repository.AuthRepository

class LoginWithEmailAndPasswordUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Response<Unit> {
        return repository.login(email = email, password = password)
    }
}