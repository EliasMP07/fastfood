package com.example.deliveryapp.delivery.domain.usecases

import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.delivery.domain.repository.DeliveryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitDeliveryOrderUseCase(
    private val repository: DeliveryRepository
) {
    suspend operator fun invoke(
        idOrder: String,
        idClient: String,
        idAddress: String,
        status: String
    ): Response<Unit> {
        return withContext(Dispatchers.IO){
            repository.initDelivery(
                idClient = idClient,
                idAddress = idAddress,
                idOrder = idOrder,
                status = status
            )
        }
    }
}