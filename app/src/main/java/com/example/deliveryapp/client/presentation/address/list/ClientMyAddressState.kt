package com.example.deliveryapp.client.presentation.address.list

import com.example.deliveryapp.client.domain.model.Address

data class ClientMyAddressState(
    val myAddress: List<Address> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val selectedAddressId: String? = null,
)
