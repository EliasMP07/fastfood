package com.example.deliveryapp.client.presentation.address.create

import com.example.deliveryapp.client.domain.model.AddressInfo

data class ClienteCreateAddressState(
    val address: String = "",
    val neighborhoodChange: String = "",
    val addressInfo: AddressInfo = AddressInfo()
)
