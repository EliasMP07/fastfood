package com.example.deliveryapp.client.domain.repository

import com.example.deliveryapp.client.domain.model.Location
import com.example.deliveryapp.client.presentation.address.models.AddressInfo
import kotlinx.coroutines.flow.Flow

interface LocationConverter {
    suspend fun getDirection(location: Location): Flow<AddressInfo>
}