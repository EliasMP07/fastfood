package com.example.deliveryapp.auth.data.mapper

import com.example.deliveryapp.auth.data.remote.RegisterRequestDto
import com.example.deliveryapp.auth.domain.model.RegisterRequest

fun RegisterRequest.toRegisterRequestDto(): RegisterRequestDto {
    return RegisterRequestDto(
        email = email,
        password = password,
        name = name,
        lastname = lastname,
        phone = phone,
        image = image,
    )
}