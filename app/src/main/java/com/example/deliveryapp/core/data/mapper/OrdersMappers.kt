package com.example.deliveryapp.core.data.mapper

import com.example.deliveryapp.core.data.remote.dto.orders.AddressClientDto
import com.example.deliveryapp.core.data.remote.dto.orders.ClientDto
import com.example.deliveryapp.core.data.remote.dto.orders.DeliveryDto
import com.example.deliveryapp.core.data.remote.dto.orders.OrderDto
import com.example.deliveryapp.core.data.remote.dto.orders.ProductClientDto
import com.example.deliveryapp.core.domain.model.order.AddressClient
import com.example.deliveryapp.core.domain.model.order.Client
import com.example.deliveryapp.core.domain.model.order.Delivery
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.core.domain.model.order.ProductClient

fun OrderDto.toOrderRestaurant(): Order {
    return Order(
        address = address.toAddressClient(),
        client = clientDto.toClient(),
        delivery = deliveryDto.toDelivery(),
        id = id,
        idAddress = idAddress,
        idClient = idClient,
        idDelivery = idDelivery?:"",
        lat = lat,
        lng = lng,
        productsClient = productClientDto.map {
            it.toProductClient()
        },
        status = status,
        timestamp = timestamp
    )
}

fun DeliveryDto.toDelivery(): Delivery {
    return Delivery(
        id = id?:"",
        image = image ?: "",
        lastname = lastname ?: "",
        name = name?:""
    )
}

fun ClientDto.toClient(): Client {
    return Client(
        id = id,
        image = image,
        lastname = lastname,
        name = name
    )
}

fun AddressClientDto.toAddressClient(): AddressClient {
    return AddressClient(
        id = id,
        address = address,
        lng = lng,
        lat = lat,
        neighborhood = neighborhood
    )
}

fun ProductClientDto.toProductClient(): ProductClient {
    return ProductClient(
        id = id,
        image1 = image1,
        image2 = image2,
        image3 = image3,
        name = name,
        price = price,
        quantity = quantity,
        description = description
    )
}