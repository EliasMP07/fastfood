package com.example.deliveryapp.restaurant.data.mapper

import com.example.deliveryapp.restaurant.data.remote.dto.DeliveryAvailableDto
import com.example.deliveryapp.restaurant.domain.model.DeliveryAvailable

fun DeliveryAvailableDto.toDeliveryAvailable(): DeliveryAvailable {
    return DeliveryAvailable(
        id = id,
        email = email,
        name = name,
        sessionToken = sessionToken,
        image = image,
        password = password,
        phone = phone,
        lastname = lastname
    )
}