package com.example.deliveryapp.client.presentation.utils

import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.presentation.home.fragments.home.model.CategoryUiModel

fun Category.toCategoryUiModel(): CategoryUiModel{
    return CategoryUiModel(
        id = id,
        name = name,
        image = image
    )
}
fun CategoryUiModel.toCategory(): Category{
    return Category(
        id = id,
        name = name,
        image = image
    )
}