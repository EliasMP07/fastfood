package com.example.deliveryapp.restaurant.data.mapper

import com.example.deliveryapp.restaurant.data.remote.dto.CategoryDto
import com.example.deliveryapp.restaurant.domain.model.Category

fun Category.toCategoryDto(): CategoryDto{
    return CategoryDto(
        id = id,
        image = image,
        name = name
    )
}


fun CategoryDto.toCategory(): Category{
    return Category(
        id = id,
        image = image,
        name = name
    )
}