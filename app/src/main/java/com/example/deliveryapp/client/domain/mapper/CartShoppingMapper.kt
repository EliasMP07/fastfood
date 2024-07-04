package com.example.deliveryapp.client.domain.mapper

import com.example.deliveryapp.client.domain.model.CartShopping
import com.example.deliveryapp.client.domain.model.CartShoppingSerializable

fun CartShoppingSerializable.toCartShopping(): CartShopping{
    return CartShopping(
        listProducts = listProduct.map {
            it.toProduct()
        }
    )
}