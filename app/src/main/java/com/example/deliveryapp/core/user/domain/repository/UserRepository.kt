package com.example.deliveryapp.core.user.domain.repository

import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.domain.model.User

interface UserRepository {
    suspend fun update(user: User): Response<User>
    suspend fun addressFavorite(idDirection: String?)
    suspend fun getAddressFavorite(): String?
}