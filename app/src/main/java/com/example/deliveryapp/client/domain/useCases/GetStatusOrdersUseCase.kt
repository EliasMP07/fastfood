package com.example.deliveryapp.client.domain.useCases

import com.example.deliveryapp.client.domain.model.OrderClient
import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.core.domain.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetStatusOrdersUseCase(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(status: String): Flow<Response<List<OrderClient>>>{
        return withContext(Dispatchers.IO){
            repository.getStatusOrders(status = status)
        }
    }
}