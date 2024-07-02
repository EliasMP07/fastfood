package com.example.deliveryapp.client.data.mapppers

import com.example.deliveryapp.client.data.network.dto.category.ProductDto
import com.example.deliveryapp.client.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        image = image1,
        image2 = image2,
        image3 = image3,
        idCategory = idCategory,
        price = price,
        ranting = ranting
    )
}