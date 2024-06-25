package com.example.deliveryapp.auth.domain.repository

import com.example.deliveryapp.auth.domain.model.Response

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Response<Unit>
}