package com.example.deliveryapp.core.user.data.mapper

import com.example.deliveryapp.core.data.RolSerializable
import com.example.deliveryapp.core.user.domain.model.Rol

fun Rol.toRolSerializable(): RolSerializable{
    return RolSerializable(
        id = id,
        image = image,
        name = name,
        route = route
    )
}

fun RolSerializable.toRol(): Rol {
    return Rol(
        id = id,
        image = image,
        name = name,
        route = route
    )
}