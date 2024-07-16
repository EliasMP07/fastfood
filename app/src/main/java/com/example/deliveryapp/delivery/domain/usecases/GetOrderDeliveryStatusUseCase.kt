package com.example.deliveryapp.delivery.domain.usecases

import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.delivery.domain.repository.DeliveryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetOrderDeliveryStatusUseCase(
    private val repository: DeliveryRepository
) {
    suspend operator fun invoke(status: String): Flow<Response<List<Order>>>{
        return withContext(Dispatchers.IO){
            repository.getMyOrdersAssign(status = status)
        }
    }
}