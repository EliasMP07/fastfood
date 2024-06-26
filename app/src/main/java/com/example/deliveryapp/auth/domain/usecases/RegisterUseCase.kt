package com.example.deliveryapp.auth.domain.usecases

import com.example.deliveryapp.auth.domain.model.RegisterRequest
import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(registerRequest: RegisterRequest): Response<Unit>{
        return repository.register(registerRequest)
    }
}