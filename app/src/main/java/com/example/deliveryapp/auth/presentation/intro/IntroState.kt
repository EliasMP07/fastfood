package com.example.deliveryapp.auth.presentation.intro

import com.example.deliveryapp.core.domain.model.User

data class IntroState(
    val isLoggedIn: Boolean = false,
    val user: User? = null
)
