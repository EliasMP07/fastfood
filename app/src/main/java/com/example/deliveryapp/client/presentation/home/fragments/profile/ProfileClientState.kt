package com.example.deliveryapp.client.presentation.home.fragments.profile

import com.example.deliveryapp.core.user.domain.model.User

data class ProfileClientState(
    val user: User = User()
)
