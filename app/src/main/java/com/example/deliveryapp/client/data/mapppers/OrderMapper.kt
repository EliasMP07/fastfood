package com.example.deliveryapp.client.data.mapppers

import com.example.deliveryapp.client.data.network.dto.orders.OrderClientDto
import com.example.deliveryapp.client.domain.model.OrderClient

fun OrderClientDto.toOrderClient(): OrderClient {
    return OrderClient(
        id = id,
        status = status,
        timestamp = timestamp,
        address = address.toAddress(),
        products = products.map {
            it.toProduct()
        }
    )
}