package com.example.deliveryapp.restaurant.domain.usecases.category

import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.restaurant.domain.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssignDeliveryUseCase(
    private val repository: RestaurantRepository
) {
    suspend operator fun invoke(
        idOrder: String,
        idClient: String,
        idAddress: String,
        idDelivery: String,
        status: String
    ): Response<Unit>{
        return withContext(Dispatchers.IO){
            repository.assignDelivery(
                idDelivery = idDelivery,
                idClient = idClient,
                idAddress = idAddress,
                idOrder = idOrder,
                status = status
            )
        }
    }
}