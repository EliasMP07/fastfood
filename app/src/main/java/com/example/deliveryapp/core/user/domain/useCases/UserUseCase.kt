package com.example.deliveryapp.core.user.domain.useCases

data class UserUseCase(
    val updateProfileUseCase: UpdateProfileUseCase,
    val addFavoriteAddressUseCase: AddFavoriteAddressUseCase,
    val getAddressFavoriteUseCase: GetAddressFavoriteUseCase
)
