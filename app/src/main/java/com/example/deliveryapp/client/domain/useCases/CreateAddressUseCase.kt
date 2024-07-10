package com.example.deliveryapp.client.domain.useCases

import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.core.domain.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateAddressUseCase(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(
        address: String,
        neighborhood: String,
        latitud: Double,
        longitud: Double
    ): Response<Unit> {
        return withContext(Dispatchers.IO) {
            repository.createAddress(
                address = address,
                neighborhood = neighborhood,
                latitud = latitud,
                longitud = longitud
            )
        }
    }
}