package com.example.deliveryapp.client.presentation.address.map

import com.example.deliveryapp.client.domain.model.AddressInfo
import com.example.deliveryapp.core.domain.model.Location

data class MapGoogleState(
    val currentLocation: Location? = null,
    val cameraPosition: Location? = null,
    val shouldFollowLocation: Boolean = true,
    val addressInfo: AddressInfo? = null
)
