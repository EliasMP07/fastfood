package com.example.deliveryapp.core.user.domain.useCases

import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.core.user.domain.repository.UserRepository

class UpdateProfileUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Response<User>{
        return repository.update(user)
    }
}