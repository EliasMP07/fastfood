package com.example.deliveryapp.client.data.mapppers

import com.example.deliveryapp.client.data.network.dto.category.CategoryDto
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.domain.model.CategorySerializable

fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        image = image,
        name = name
    )
}

fun Category.toCategorySerializable(): CategorySerializable {
    return CategorySerializable(
        id = id,
        name = name,
        image = image
    )
}
fun CategorySerializable.toCategory(): Category{
    return Category(
        id = id,
        name = name,
        image = image
    )
}