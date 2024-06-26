package com.example.deliveryapp.auth.data.mapper

import com.example.deliveryapp.auth.data.remote.dto.login.UserDto
import com.example.deliveryapp.core.domain.model.User

fun UserDto.toUser(): User{
    return User(
        id = id,
        name = name,
        lastname = lastname,
        phone = phone,
        image = image,
        email = email,
        sessionToken = sessionToken
    )
}