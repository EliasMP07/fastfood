package com.example.deliveryapp.auth.domain.repository

import com.example.deliveryapp.auth.domain.model.RegisterRequest
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.domain.model.User

interface AuthRepository {

    suspend fun login(email: String, password: String): Response<User>
    suspend fun register(registerRequest: RegisterRequest): Response<Unit>

}