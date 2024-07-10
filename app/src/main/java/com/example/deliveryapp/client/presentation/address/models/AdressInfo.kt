package com.example.deliveryapp.client.presentation.address.models

import kotlinx.serialization.Serializable

@Serializable
data class AddressInfoSerializable(
    val address: String = "",
    val country: String = "",
    val city: String = "",
    val lat: Double = 0.0,
    val log: Double = 0.0
)

data class AddressInfo(
    val address: String = "",
    val country: String = "",
    val city: String = "",
    val lat: Double = 0.0,
    val log: Double = 0.0
)

fun AddressInfoSerializable.toAddressInfo(): AddressInfo {
    return AddressInfo(
        address = address,
        country = country,
        city = city,
        log =  log,
        lat = lat
    )
}

fun AddressInfo.toAddressSerializable(): AddressInfoSerializable {
    return AddressInfoSerializable(
        address = address,
        country = country,
        city = city,
        log =  log,
        lat = lat
    )
}