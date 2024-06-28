package com.example.deliveryapp.client.presentation.home

import com.example.deliveryapp.core.domain.model.User

data class ClientHomeState(
    val user: User = User()
)
