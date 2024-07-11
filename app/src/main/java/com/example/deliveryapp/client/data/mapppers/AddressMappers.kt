package com.example.deliveryapp.client.data.mapppers

import com.example.deliveryapp.client.data.network.dto.AddressDto
import com.example.deliveryapp.client.domain.model.Address

fun AddressDto.toAddress(): Address {
    return Address(
        id = id,
        idUser =  idUser?:"",
        lat = lat,
        lng = lng,
        address = address,
        neighborhood = neighborhood?:""
    )
}