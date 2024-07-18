package com.example.deliveryapp.delivery.presentation.mapDelivery

import com.example.deliveryapp.client.domain.model.AddressInfo
import com.example.deliveryapp.core.domain.model.Location
import com.example.deliveryapp.core.domain.model.order.Order

data class DeliveryMapState(
    val currentLocation: Location? = null,
    val cameraPosition: Location? = null,
    val shouldFollowLocation: Boolean = true,
    val addressInfo: AddressInfo? = null,
    val order: Order? = null
)
