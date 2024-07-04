package com.example.deliveryapp

import com.example.deliveryapp.core.user.domain.model.User

data class MainState(
    val isLoggedIn: Boolean = false,
    val user: User? = null,
    val isLoading: Boolean = true,
    val isCheckAuth: Boolean = true
)
