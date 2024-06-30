package com.example.deliveryapp.selectRol

import com.example.deliveryapp.core.user.domain.model.Rol
import com.example.deliveryapp.core.user.domain.model.User

data class SelectRolState(
    val roles: List<Rol> = listOf(),
    val user: User = User()
)
