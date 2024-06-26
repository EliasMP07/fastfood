package com.example.deliveryapp.core.domain.repository

import com.example.deliveryapp.core.domain.model.User

interface SessionStorage {
    suspend fun get(): User?
    suspend fun set(user: User?)
}