package com.example.deliveryapp.client.presentation.address.map

import com.example.deliveryapp.client.domain.model.Location
import com.example.deliveryapp.client.presentation.address.models.AddressInfo

data class MapGoogleState(
    val currentLocation: Location? = null,
    val cameraPosition: Location? = null,
    val shouldFollowLocation: Boolean = true,
    val addressInfo: AddressInfo? = null
)
