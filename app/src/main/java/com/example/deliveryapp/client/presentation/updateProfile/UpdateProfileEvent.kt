package com.example.deliveryapp.client.presentation.updateProfile

import com.example.deliveryapp.core.user.domain.model.User

sealed interface UpdateProfileEvent {
    data class Success(val user: User): UpdateProfileEvent
    data class Error(val message: String): UpdateProfileEvent
}