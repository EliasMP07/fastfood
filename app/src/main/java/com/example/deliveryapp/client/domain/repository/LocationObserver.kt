package com.example.deliveryapp.client.domain.repository

import com.example.deliveryapp.client.domain.model.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
}