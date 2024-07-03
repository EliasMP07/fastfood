package com.example.deliveryapp.client.domain.model

import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class CartShopping(
    val listProduct: List<ProductSerializable> = persistentListOf()
)
