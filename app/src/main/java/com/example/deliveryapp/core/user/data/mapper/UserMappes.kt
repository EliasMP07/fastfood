package com.example.deliveryapp.core.user.data.mapper

import com.example.deliveryapp.core.data.UserSerializable
import com.example.deliveryapp.core.user.data.network.dto.RolDto
import com.example.deliveryapp.core.user.data.network.dto.UserDto
import com.example.deliveryapp.core.user.data.network.dto.UserRequestDto
import com.example.deliveryapp.core.user.domain.model.Rol
import com.example.deliveryapp.core.user.domain.model.User

fun User.toUserSerializable(): UserSerializable{
    return UserSerializable(
        id = id,
        name = name,
        lastname = lastname,
        phone = phone,
        image = image,
        email = email,
        sessionToken = sessionToken,
        roles = roles?.map {
            it.toRolSerializable()
        }
    )
}

fun User.toUserRequestDto(): UserRequestDto {
    return UserRequestDto(
        id = id,
        name = name,
        lastname = lastname,
        phone = phone,
        image = image,
        email = email
    )
}

fun UserSerializable.toUser(): User {
    return User(
        id = id,
        name = name,
        lastname = lastname,
        phone = phone,
        image = image,
        email = email,
        sessionToken = sessionToken,
        roles = roles?.map {
            it.toRol()
        }
    )
}

fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        lastname = lastname,
        phone = phone,
        image = image,
        email = email,
        sessionToken = sessionToken,
        roles = roles?.map {
            it.toRol()
        }
    )
}

fun RolDto.toRol(): Rol {
    return Rol(
        id = id,
        name = name,
        image = image,
        route = route
    )
}