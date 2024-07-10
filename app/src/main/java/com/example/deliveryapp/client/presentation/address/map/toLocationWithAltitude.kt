package com.example.deliveryapp.client.presentation.address.map

import android.location.Location
import com.example.deliveryapp.client.domain.model.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.example.deliveryapp.client.domain.model.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude
    )
}