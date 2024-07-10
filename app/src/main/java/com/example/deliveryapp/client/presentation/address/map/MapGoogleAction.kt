package com.example.deliveryapp.client.presentation.address.map

import com.example.deliveryapp.client.domain.model.Location

sealed interface MapGoogleAction {
    data class OnDirectionChange(val camaraPosition: Location): MapGoogleAction
}