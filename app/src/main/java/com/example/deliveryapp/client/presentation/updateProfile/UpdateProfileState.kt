package com.example.deliveryapp.client.presentation.updateProfile

import com.example.deliveryapp.core.user.domain.model.User

data class UpdateProfileState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val imageUpload: String = ""
)
