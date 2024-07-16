package com.example.deliveryapp.restaurant.domain.usecases.category

import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.restaurant.domain.model.DeliveryAvailable
import com.example.deliveryapp.restaurant.domain.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetDeliveriesAvailableUseCase(
    private val repository: RestaurantRepository
) {
    suspend operator fun invoke(): Flow<Response<List<DeliveryAvailable>>>{
        return withContext(Dispatchers.IO){
            repository.getAvailableDelivery()
        }
    }
}