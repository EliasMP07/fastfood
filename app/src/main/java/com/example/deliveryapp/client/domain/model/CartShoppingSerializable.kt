package com.example.deliveryapp.client.domain.model

import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class CartShoppingSerializable(
    val listProduct: List<ProductSerializable> = persistentListOf()
)
