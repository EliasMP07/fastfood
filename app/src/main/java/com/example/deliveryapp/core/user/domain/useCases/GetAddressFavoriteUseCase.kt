package com.example.deliveryapp.core.user.domain.useCases

import com.example.deliveryapp.core.user.domain.repository.UserRepository

class GetAddressFavoriteUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): String?{
        return repository.getAddressFavorite()
    }
}