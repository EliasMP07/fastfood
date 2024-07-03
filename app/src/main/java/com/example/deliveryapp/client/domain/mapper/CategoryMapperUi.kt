package com.example.deliveryapp.client.domain.mapper

import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.domain.model.CategorySerializable

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