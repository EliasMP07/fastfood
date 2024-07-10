package com.example.deliveryapp.core.user.domain.useCases

import com.example.deliveryapp.core.user.domain.repository.UserRepository

class AddFavoriteAddressUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(idAddress: String){
        repository.addressFavorite(idAddress)
    }
}