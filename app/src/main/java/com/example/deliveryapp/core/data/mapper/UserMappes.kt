package com.example.deliveryapp.core.data.mapper

import com.example.deliveryapp.core.data.UserSerializable
import com.example.deliveryapp.core.domain.model.User

fun User.toUserSerializable(): UserSerializable{
    return UserSerializable(
        id = id,
        name = name,
        lastname = lastname,
        phone = phone,
        image = image,
        email = email,
        sessionToken = sessionToken
    )
}

fun UserSerializable.toUser(): User{
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