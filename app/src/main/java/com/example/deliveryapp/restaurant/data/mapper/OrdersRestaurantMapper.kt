package com.example.deliveryapp.restaurant.data.mapper

import com.example.deliveryapp.restaurant.data.remote.dto.AddressClientDto
import com.example.deliveryapp.restaurant.data.remote.dto.ClientDto
import com.example.deliveryapp.restaurant.data.remote.dto.DeliveryDto
import com.example.deliveryapp.restaurant.data.remote.dto.OrderRestaurantDto
import com.example.deliveryapp.restaurant.data.remote.dto.ProductClientDto
import com.example.deliveryapp.restaurant.domain.model.AddressClient
import com.example.deliveryapp.restaurant.domain.model.Client
import com.example.deliveryapp.restaurant.domain.model.Delivery
import com.example.deliveryapp.restaurant.domain.model.OrderRestaurant
import com.example.deliveryapp.restaurant.domain.model.ProductClient

fun OrderRestaurantDto.toOrderRestaurant(): OrderRestaurant {
    return OrderRestaurant(
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