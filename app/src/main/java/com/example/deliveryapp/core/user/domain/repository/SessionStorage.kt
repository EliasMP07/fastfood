package com.example.deliveryapp.core.user.domain.repository

import com.example.deliveryapp.core.user.domain.model.User

interface SessionStorage {
    suspend fun get(): User?
    suspend fun set(user: User?)
}