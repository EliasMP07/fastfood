package com.example.deliveryapp.core.domain.repository

import com.example.deliveryapp.core.domain.model.Location
import com.example.deliveryapp.core.domain.model.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
    suspend fun getLocation(interval: Long): Location
}