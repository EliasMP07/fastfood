package com.example.deliveryapp.client.data.mapppers

import com.example.deliveryapp.client.data.network.dto.category.CategoryDto
import com.example.deliveryapp.client.domain.model.Category

fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        image = image,
        name = name
    )
}