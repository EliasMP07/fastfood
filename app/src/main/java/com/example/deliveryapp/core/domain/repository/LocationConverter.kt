package com.example.deliveryapp.core.domain.repository

import com.example.deliveryapp.client.domain.model.AddressInfo
import com.example.deliveryapp.core.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationConverter {
    suspend fun getDirection(location: Location): Flow<AddressInfo>
}