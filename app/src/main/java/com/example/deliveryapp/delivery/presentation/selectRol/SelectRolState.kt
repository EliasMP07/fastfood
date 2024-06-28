package com.example.deliveryapp.delivery.presentation.selectRol

import com.example.deliveryapp.core.domain.model.Rol
import com.example.deliveryapp.core.domain.model.User

data class SelectRolState(
    val roles: List<Rol> = listOf(),
    val user: User = User()
)
