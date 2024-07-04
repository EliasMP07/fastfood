package com.example.deliveryapp.auth.presentation.intro

import com.example.deliveryapp.core.user.domain.model.User

data class IntroState(
    val isLoggedIn: Boolean = false,
    val user: User? = null,
    val isLoading: Boolean = false,
)
